package parimi.com.bakify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.utils.BakeUtils;
import parimi.com.bakify.utils.Constants;
import parimi.com.bakify.utils.network.*;

import cz.msebera.android.httpclient.Header;
import parimi.com.bakify.adapter.BakeAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.bake_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getBakeReceipeList();
    }

    public void getBakeReceipeList() {
        RequestParams rp = new RequestParams();
        HttpUtils.getReceipes(Constants.BAKING_JSON, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {

                    BakeReceipe[] bakeReceipeArray = new BakeReceipe[response.length()];
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i < jsonArray.length(); i++) {
                        BakeReceipe bakeReceipe = BakeUtils.convertJsonToObject((JSONObject)jsonArray.get(i));
                        bakeReceipeArray[i] = bakeReceipe;
                    }
                    mAdapter = new BakeAdapter(bakeReceipeArray);
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
