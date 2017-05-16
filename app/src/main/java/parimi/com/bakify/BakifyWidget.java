package parimi.com.bakify;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import parimi.com.bakify.model.BakeIngredients;
import parimi.com.bakify.service.MyWidgetRemoteViewsService;
import parimi.com.bakify.utils.BakeUtils;

/**
 * Implementation of App Widget functionality.
 */
public class BakifyWidget extends AppWidgetProvider {

    ArrayList<BakeIngredients> ingredientsList;
    String recipe;

    /**
     * This method updates the widget with ingredients list.
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bakify_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtra(context.getString(R.string.ingredients_param), (new Gson().toJson(ingredientsList)).toString());
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.appwidget_ingredients, intent);
        views.setTextViewText(R.id.appwidget_recipe, recipe);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * This method is invoked by OnRecieve method, this method updates the widget with required information.
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

    /**
     * This method is invoked when app broadcasts any change in the selected recipe.
     * This method extracts the ingredients list and invokes onUpdate method.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            String ingredientsJson = extras.getString(context.getString(R.string.ingredients_param));
            recipe = extras.getString(context.getString(R.string.recipe_param));
            if (ingredientsJson != null) {
                JSONArray ingredientJsonArray = new JSONArray(ingredientsJson);
                ingredientsList = BakeUtils.convertJsonToIngredientsList(ingredientJsonArray, context);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisAppWidget = new ComponentName(context.getPackageName(), BakifyWidget.class.getName());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

                onUpdate(context, appWidgetManager, appWidgetIds);
            }
            super.onReceive(context, intent);

        } catch (Exception e) {

        }
    }
}

