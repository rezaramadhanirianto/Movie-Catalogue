package com.example.asus.finalsubmissionr2.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetServices : RemoteViewsService(){
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory =
        StacksRemoteFactory(this.applicationContext)


}
