package parimi.com.bakify.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by nandpa on 5/15/17.
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}