package parimi.com.bakify.service;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import parimi.com.bakify.R;
import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.utils.BakeUtils;

/**
 * This class is used to populate widget with ingredients for the selected recipe
 */

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<BakeIngredients> ingredients;
    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        try {
            String ingredients = intent.getExtras().get(applicationContext.getString(R.string.ingredients_param)).toString();
            this.ingredients = BakeUtils.convertJsonToIngredientsList(new JSONArray(ingredients), applicationContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredients != null ) {
            return ingredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_detail_view);
        rv.setTextViewText(R.id.ingredients_name_txt_view, ingredients.get(position).getIngredient());
        rv.setTextViewText(R.id.ingredients_measurements_txt_view, ingredients.get(position).getQuantity() + " " +ingredients.get(position).getMeasure());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}