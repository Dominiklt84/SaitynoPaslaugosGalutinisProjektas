import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MovieStepDefs {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String HOST = "http://localhost:8099";

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
    public void existingMovie() {

        movieId = 3L;
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

        }
    }

    @When("user requests top movies this month")
    public void userRequestsTopMoviesThisMonth() throws IOException {

        Request request = new Request.Builder().url(HOST + "/api/movies/top/month").get().build();

        try (Response response = client.newCall(request).execute()) {

            statusCode = response.code();

        }
    }

    @When("user creates new movie")
    public void userCreatesNewMovie() throws IOException {

        String json = """
                {
                  "title": "BDD Test Movie123",
                  "year": "2025",
                  "released": "01 Jan 2025",
                  "runtime": "120 min",
                  "plot": "Test plot",
                  "rated": {
                    "id": 2
                  }
                }
        """;

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
          "title":"Updated Movie"
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

    @Then("movie list should not be empty")
    public void movieListShouldNotBeEmpty() {

        assertFalse(responseString.isEmpty());
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

    @Then("top month movies response should not be empty")
    public void topMonthMoviesResponseShouldNotBeEmpty() {

        assertFalse(responseString.isEmpty());
    }

    @Then("movie should be created")
    public void movieShouldBeCreated() {

        assertEquals(201, statusCode);

        assertTrue(responseString.contains("BDD Test Movie123"));
    }

    @Then("updated movie should be returned")
    public void updatedMovieShouldBeReturned() {

        assertEquals(200, statusCode);

        assertTrue(responseString.contains("Updated Movie"));
    }
}