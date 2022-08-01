import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.enqueue
import com.example.GetFooQuery
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BaseResponse

class MainTest {
  @Test
  fun test() {
    runBlocking {

      val mockServer = MockServer()
      val apolloClient = ApolloClient.Builder().serverUrl(mockServer.url()).build()

      mockServer.enqueue("""
      {
        "errors": [
          {
            "message": "Server Error",
            "extensions": {
              "status": 410,
              "code": "INTERNAL_SERVER_ERROR",
              "path": [
                "orders"
              ]
            }
          },
          {
            "message": "Cannot return null for non-nullable field Query.orders.",
            "extensions": {
              "status": 500,
              "code": "INTERNAL_SERVER_ERROR",
              "path": [
                "orders"
              ]
            }
          }
        ],
        "data": null
      }
    """.trimIndent())

      val httpHeaders = listOf(HttpHeader("key", "value"))
      apolloClient.query(GetFooQuery()).httpHeaders(httpHeaders).execute().also {
        var baseResponse: BaseResponse? = null

        // Convert the data to BaseResponse
        baseResponse = it.data?.let {
          BaseResponse()
        }

        if (baseResponse != null) {
          println("Sucess")
        } else {
          assert(it.errors!!.size == 2)
          println("Errors: ${it.errors}")
        }
      }
    }
  }
}