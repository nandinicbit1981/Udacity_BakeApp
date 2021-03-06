package parimi.com.bakify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import parimi.com.bakify.adapter.BakeAdapter;
import parimi.com.bakify.model.BakeReceipe;
import parimi.com.bakify.utils.BakeUtils;
import parimi.com.bakify.utils.Constants;
import parimi.com.bakify.utils.network.HttpUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.bake_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.num_columns));
        mRecyclerView.setLayoutManager(mLayoutManager);
        getBakeReceipeList();
    }

    public void getBakeReceipeList() {
        RequestParams rp = new RequestParams();
        try {
            HttpUtils.getReceipes(Constants.BAKING_JSON, rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    try {

                        ArrayList<BakeReceipe> bakeReceipeArray = new ArrayList<>();
                        JSONArray jsonArray = response;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BakeReceipe bakeReceipe = BakeUtils.convertJsonToBakeReceipe((JSONObject) jsonArray.get(i), getBaseContext());
                            bakeReceipeArray.add(bakeReceipe);
                        }

                        mAdapter = createAdapter(bakeReceipeArray);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }catch(Exception e) {
           Log.e(TAG, e.getMessage());
        }
    }

    private BakeAdapter createAdapter(ArrayList<BakeReceipe> results) {
        return new BakeAdapter(results, new BakeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BakeReceipe item) {

                Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                try {
                    Gson gson = new Gson();
                    String receipeJson = gson.toJson(item);
                    intent.putExtra(getString(R.string.recipe_param), receipeJson);

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                startActivity(intent);
            }

        });
    }
}
