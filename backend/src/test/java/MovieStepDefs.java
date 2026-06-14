import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MovieStepDefs {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String HOST = "http://localhost:8099";

    private final ObjectMapper mapper = new ObjectMapper();

    private final OkHttpClient client = new OkHttpClient();

    private Long movieId;

    private String responseString;

    private int statusCode;

    @Given("movie service is running")
    public void movieServiceIsRunning() {
    }

    @Given("movie data is prepared")
    public void movieDataIsPrepared() {
    }

    @Given("existing movie")
    public void existingMovie() throws Exception {

        String title = "Test_" + System.currentTimeMillis();

        String body = """
        {
          "title": "%s",
          "year": "2025",
          "released": "01 Jan 2025",
          "runtime": "120 min",
          "plot": "Test plot",
          "rated": {
            "id": 2
          }
        }
        """.formatted(title);

        Request request = new Request.Builder().url(HOST + "/api/movies")
                .post(RequestBody.create(body, JSON)).build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            assertEquals(201, statusCode);

            assertNotNull(response.body());

            responseString = response.body().string();

            JsonNode node = mapper.readTree(responseString);

            assertTrue(node.has("id"), "Response does not contain id field");

            movieId = node.get("id").asLong();
        }
    }

    @Given("no movies exist")
    public void noMoviesExist() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies").get().build();

        try (Response response = client.newCall(request).execute()) {

            if (response.code() == 404) {
                return;
            }

            String body = response.body().string();

            JsonNode root = mapper.readTree(body);

            JsonNode movies = root.path("_embedded").path("movieList");

            for (JsonNode movie : movies) {

                long id = movie.get("id").asLong();

                Request deleteRequest = new Request.Builder().url(HOST + "/api/movies/" + id).delete()
                        .build();

                client.newCall(deleteRequest).execute().close();
            }
        }
    }

    @When("user requests all movies")
    public void userRequestsAllMovies() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies").get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            responseString = response.body().string();
        }
    }

    @When("user requests movie with id {int}")
    public void userRequestsMovieWithId(int id) throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/" + id).get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            if (response.body() != null) {

                responseString = response.body().string();
            }
        }
    }

    @When("user searches for {string}")
    public void userSearchesFor(String title) throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/omdb?title=" + title).get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            if (response.body() != null) {

                responseString = response.body().string();
            }
        }
    }

    @When("user requests top movies today")
    public void userRequestsTopMoviesToday() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/top/day").get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            if (response.body() != null) {

                responseString = response.body().string();
            } else {

                responseString = "";
            }

        }
    }

    @When("user requests top movies this month")
    public void userRequestsTopMoviesThisMonth() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/top/month").get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            if (response.body() != null) {

                responseString = response.body().string();
            } else {

                responseString = "";
            }

        }
    }

    @When("user creates new movie")
    public void userCreatesNewMovie() throws IOException {

        String title = "BDD_Test_" + System.currentTimeMillis();

        String json = """
                {
                  "title": "%s",
                  "year": "2025",
                  "released": "01 Jan 2025",
                  "runtime": "120 min",
                  "plot": "Test plot",
                  "rated": {
                    "id": 2
                  }
                }
        """.formatted(title);

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder().url(HOST + "/api/movies").post(body).build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            responseString = response.body().string();
        }
    }

    @When("user updates movie")
    public void userUpdatesMovie() throws IOException {

        String json = """
        {
          "title":"Updated Movied",
          "year":"2025",
          "released":"01 Jan 2025",
          "runtime":"150 min",
          "plot":"Updated plot",
          "rated":{
            "id":2
          }
        }
        """;

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder().url(HOST + "/api/movies/" + movieId).put(body).build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            responseString = response.body().string();
        }
    }

    @When("user deletes movie")
    public void userDeletesMovie() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/" + movieId).delete().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();
        }
    }

    @When("user deletes movie with id {int}")
    public void userDeletesMovieWithId(int id) throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/" + id).delete().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();
        }
    }

    @When("user requests existing movie")
    public void userRequestsExistingMovie() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/" + movieId).get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            if (response.body() != null) {
                responseString = response.body().string();
            }
        }
    }

    @When("user requests movie with created id")
    public void userRequestsCreatedMovie() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/" + movieId).get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            if (response.body() != null) {

                responseString = response.body().string();
            }
        }
    }

    @When("user updates movie with id {int}")
    public void userUpdatesMovieWithId(int id) throws IOException {

        String json = """
        {
          "title":"Updated Movie"
        }
        """;

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder().url(HOST + "/api/movies/" + id).put(body).build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

            if (response.body() != null) {

                responseString = response.body().string();
            }
        }
    }

    @Then("movie list should not be empty")
    public void movieListShouldNotBeEmpty() {

        assertNotNull(responseString);

        assertFalse(responseString.isBlank());
    }

    @Then("response should contain {string}")
    public void responseShouldContain(String text) {

        assertTrue(responseString.contains(text));
    }

    @Then("response status should be {int}")
    public void responseStatusShouldBe(int status) {

        assertEquals(status, statusCode);
    }

    @Then("top movies response should not be empty")
    public void topMoviesResponseShouldNotBeEmpty() {

        assertFalse(responseString.isEmpty());
    }

    @Then("movie should be created")
    public void movieShouldBeCreated() {

        assertEquals(201, statusCode);

        assertNotNull(responseString);
    }

    @Then("updated movie should be returned")
    public void updatedMovieShouldBeReturned() {

        assertEquals(200, statusCode);

        assertNotNull(responseString);
    }
}