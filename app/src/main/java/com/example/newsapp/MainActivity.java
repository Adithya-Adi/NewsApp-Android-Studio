package com.example.newsapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<NewsArticle> mArticleList;

    ProgressDialog pd;
    private ArticleAdapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_id);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArticleList = new ArrayList<>();
        //add your api key here
        new JsonTask().execute("https://newsdata.io/api/1/news?apikey=API_KEY&country=in&&language=en");
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            try {
                JSONObject response = new JSONObject(result);
                JSONArray articles = response.getJSONArray("results");
                for (int j = 0; j < articles.length(); j++) {
                    JSONObject article = articles.getJSONObject(j);
                    //System.out.println(article);
                    NewsArticle currentArticle = new NewsArticle();

                    String title = article.getString("title");
                    String description = article.getString("description");
                    String url = article.getString("link");
                    String urlToImage = article.getString("image_url");
                    String publishedAt = article.getString("pubDate");
                    String content = article.getString("content");

                    currentArticle.setTitle(title);
                    currentArticle.setDescription(description);
                    currentArticle.setUrl(url);
                    currentArticle.setUrlToImage(urlToImage);
                    currentArticle.setPublishedAt(publishedAt);
                    currentArticle.setContent(content);

                    mArticleList.add(currentArticle);
                    mArticleAdapter=new ArticleAdapter(getApplicationContext(),mArticleList);
                    mRecyclerView.setAdapter(mArticleAdapter);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
    }

}
