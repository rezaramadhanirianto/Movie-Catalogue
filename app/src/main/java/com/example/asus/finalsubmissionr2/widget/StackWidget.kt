package com.example.asus.finalsubmissionr2.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.example.asus.finalsubmissionr2.R

/**
 * Implementation of App Widget functionality.
 */
class StackWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "com.example.asus.stackwidget"
        const val EXTRA_ITEM = "com.example.asus.stackwidget.EXTRA_ITEM"
        //pindahkan fungsi ini ke companion object, karena kita akan memanggil fungsi ini dari luar kelas
        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StackWidgetServices::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val views = RemoteViews(context.packageName, R.layout.stack_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)
            val toastIntent = Intent(context, StackWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null) {
            if (intent.action != null) {
                if (intent.action == TOAST_ACTION) {
                    val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                    Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

