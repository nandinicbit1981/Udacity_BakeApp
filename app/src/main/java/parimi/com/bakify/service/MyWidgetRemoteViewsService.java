package parimi.com.bakify.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * This service is used for communication between app and widget
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}