import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RemoteTest {
    private val LOGGER = LoggerFactory.getLogger(CategoryTest::class.java)
    private var httpClient: HttpClient = HttpClient.newHttpClient()

    @Test
    fun `Checklist`() {
        //create category
        val insertUri = URI("http://92.53.124.44:8080/categories/insert")
        val bodyPublisher = HttpRequest.BodyPublishers.ofString("{\"id\":2,\"name\":\"junit_category\"}")
        val httpRequestInsert: HttpRequest = HttpRequest.newBuilder(insertUri)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer: test")
            .POST(bodyPublisher)
            .build()

        val postResponse = httpClient.send(httpRequestInsert, HttpResponse.BodyHandlers.ofString())
        println("POST response: $postResponse")

        //check it exists
        var uri = URI("http://92.53.124.44:8080/categories")
        var httpRequest: HttpRequest = HttpRequest.newBuilder(uri).GET().build()
        var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        println("All categories: " + response.body())
        var jsonArray = JSONArray(response.body());
        println("Name of the last category: " + jsonArray.getJSONObject(jsonArray.length() - 1).getString("name"))
        var result = jsonArray.getJSONObject(jsonArray.length() - 1).getString("name")
        var categoryId = jsonArray.getJSONObject(jsonArray.length() - 1).getString("id");
        Assertions.assertEquals("junit_category", result)

        val getCategoryByIdURI = URI("http://92.53.124.44:8080/categories/$categoryId")
        val getCategoryByIdRequest = HttpRequest.newBuilder(getCategoryByIdURI).GET().build()
        val category = httpClient.send(getCategoryByIdRequest, HttpResponse.BodyHandlers.ofString())
        Assertions.assertNotNull(category)

        val getAvailableFundsBeforeIncome = HttpRequest.newBuilder(URI("http://92.53.124.44:8080/users/test"))
            .header("Authorization", "\$2a\$10\$FMWOkkzBrujIEIlzCMHED.fDTpR./ANKoFdtHF387/tVqG.CgnVBi")
            .GET()
            .build()
        val response2 = httpClient.send(getAvailableFundsBeforeIncome, HttpResponse.BodyHandlers.ofString())
        val availableFundsBeforeIncome = JSONObject(response2.body()).getDouble("availableFunds")
        println("Available funds before income: $availableFundsBeforeIncome")

        //insert income, using this category
        val valueToEncode = "test:test"
        val encodedAuth =
            "Basic" + Base64.getEncoder().encodeToString(valueToEncode.toByteArray())
        val insertIncomeRequest = HttpRequest.newBuilder(URI("http://92.53.124.44:8080/transactions/test"))
            .header("Content-Type", "application/json")
            .header("Authorization", encodedAuth)
            .POST(HttpRequest.BodyPublishers.ofString(
                "{\"transactionType\":\"INCOME\", " +
                        "\"amount\":12, " +
                        "\"comment\":\"JunitComment\", " +
                        "\"categoryId\":$categoryId}"))
            .build()
        val insertIncomeResponse = httpClient.send(insertIncomeRequest, HttpResponse.BodyHandlers.ofString())
        println("Insert income response = $insertIncomeResponse")

        //check that amount of money on the account increased
        val getAvailableFundsAfterIncome = HttpRequest.newBuilder(URI("http://92.53.124.44:8080/users/test"))
            .header("Authorization", encodedAuth)
            .GET()
            .build()
        val response1 = httpClient.send(getAvailableFundsAfterIncome, HttpResponse.BodyHandlers.ofString())
        val availableFundsAfterIncome = JSONObject(response1.body()).getDouble("availableFunds")
        println("Available funds after income $availableFundsAfterIncome")
        Assertions.assertEquals(12.0, availableFundsAfterIncome - availableFundsBeforeIncome)


        //delete transaction
        val transactionId = JSONObject(insertIncomeResponse.body()).getInt("id")
        val deleteIncomeUri = URI("http://92.53.124.44:8080/transactions/$transactionId")
        val httpRequestDeleteIncome = HttpRequest.newBuilder(deleteIncomeUri)
            .DELETE()
            .header("Authorization", encodedAuth)
            .build()
        httpClient.send(httpRequestDeleteIncome, HttpResponse.BodyHandlers.ofString())

        //check that amount of money on the account decreased
        val getAvailableFundsAfterDeletingIncome = HttpRequest.newBuilder(URI("http://92.53.124.44:8080/users/test"))
            .header("Authorization", encodedAuth)
            .GET()
            .build()
        val responseAfterDeletingIncome = httpClient.send(getAvailableFundsAfterDeletingIncome, HttpResponse.BodyHandlers.ofString())
        val availableFundsAfterDeletingIncome = JSONObject(responseAfterDeletingIncome.body()).getDouble("availableFunds")
        println("Available funds after deleting income $availableFundsAfterDeletingIncome")
        Assertions.assertEquals(availableFundsBeforeIncome, availableFundsAfterDeletingIncome)


        //delete category
        val deleteUri = URI("http://92.53.124.44:8080/categories/delete/$categoryId")
        val httpRequestDelete = HttpRequest.newBuilder(deleteUri)
            .DELETE()
            .header("Authorization", "Bearer: test")
            .build()

        httpClient.send(httpRequestDelete, HttpResponse.BodyHandlers.ofString())
        val responseAfterDeleting = httpClient.send(getCategoryByIdRequest, HttpResponse.BodyHandlers.ofString())
        val jsonObject = JSONObject(responseAfterDeleting.body())
        val errorText = jsonObject.getString("error")
        Assertions.assertEquals("Such category is not exists!", errorText)
    }
}