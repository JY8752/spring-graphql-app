package com.example.springgraphqlapp.application.service

import com.example.springgraphqlapp.domain.repository.PageContentRepository
import com.example.springgraphqlapp.infrastructure.entity.PageContentEntity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

private const val CONNECT_TIMEOUT = 5 * 1000L
private const val READ_TIMEOUT = 5 * 1000L
private const val WRITE_TIMEOUT = 5 * 1000L
private const val CALL_TIMEOUT = 5 * 1000L

/**
 * サイトコンテンツの取得と解析をするサービスクラス
 */
@Service
class PageContentService(
    private val pageContentRepository: PageContentRepository,
    private val textIndexService: TextIndexService,
    private val webElementIndexService: WebElementIndexService
) {

    private var client: OkHttpClient? = null

    @PostConstruct
    fun init() {
        client = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .callTimeout(CALL_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    /**
     * エラー理由
     */
    sealed class ErrorReason {
        data class IsNull(val code: Int?) : ErrorReason()
        data class IsFailed(val code: Int?) : ErrorReason()
        data class BodyNullOrBlank(val code: Int?) : ErrorReason()
        object ConnectionError : ErrorReason()
    }

    /**
     * レスポンスからエラーであればエラー理由を返す
     */
    private fun getErrorReason(response: Response?): ErrorReason? = when {
        response === null -> ErrorReason.IsNull(null)
        !response.isSuccessful -> ErrorReason.IsFailed(response.code)
        response.body === null -> ErrorReason.BodyNullOrBlank(response.code)
        else -> null
    }

    /**
     * エラー項目名を取得する
     */
    private fun getErrorReasonName(errorReason: ErrorReason): String = errorReason.javaClass.simpleName

    /**
     * 指定されたURLに対してhttpリクエストを実施しコンテンツを解析する
     */
    fun analyzeUrl(url: String): ObjectId {
        val request = Request.Builder().url(url).build()
        var contentId: ObjectId? = null

        //httpRequest実施
        runCatching {
            client?.newCall(request)?.execute()
        }.onSuccess {
            it.use { response ->
                contentId = when(val errorReason = getErrorReason(response)) {
                    null -> {
                        //成功
                        val html = response?.body?.string().orEmpty()

                        //本文抽出
                        val textIndexId = textIndexService.extractMainText(html)

                        //形態素解析
                        textIndexId?.let { id -> textIndexService.morphologicalAnalyze(id) }

                        //html要素解析
                        val webElementIndexId = webElementIndexService.analyzeElements(html)

                        registerPageContent(
                            url = url,
                            textIndexId = textIndexId,
                            webElementIndexId = webElementIndexId,
                            code = response?.code)
                    }
                    //失敗
                    else -> registerPageContent(url = url, isError = true, errorReason = errorReason, code = response?.code)
                }
            }
        }.onFailure {
            //リクエスト自体が失敗
            contentId = registerPageContent(url = url, isError = true, errorReason = ErrorReason.ConnectionError)
        }

        return contentId!!
    }

    /**
     * PageContentレコードを新規で登録する
     */
    private fun registerPageContent(
        url: String,
        textIndexId: ObjectId? = null,
        webElementIndexId: ObjectId? = null,
        isError: Boolean = false,
        errorReason: ErrorReason? = null,
        code: Int? = null
    ): ObjectId {
        return pageContentRepository.save(PageContentEntity(
            url = url,
            textIndexId = textIndexId,
            webElementIndexId = webElementIndexId,
            isError = isError,
            errorReason = errorReason?.let { getErrorReasonName(it) },
            code = code)).contentId
    }
}