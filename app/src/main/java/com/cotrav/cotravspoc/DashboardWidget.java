package com.cotrav.cotravspoc;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import com.cotrav.cotravspoc.models.dashboardmodel.DashBoardApiResponse;
import com.cotrav.cotravspoc.models.dashboardmodel.DashBoardData;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.GetDashBoardDataAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class DashboardWidget extends AppWidgetProvider {

    static TextView txtTotal;

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence txt = "Bookings Pending for Approval";
        String token;
        String employeeID;
        GetDashBoardDataAPI loginAPI;
        final SharedPreferences loginpref, dashboardPref;
        loginpref = context.getSharedPreferences("login_info", MODE_PRIVATE);
        dashboardPref = context.getSharedPreferences("dashboard_info", MODE_PRIVATE);
        token = loginpref.getString("access_token", "n");
        employeeID = loginpref.getString("user_id", "n");
        loginAPI = ConfigRetrofit.configRetrofit(GetDashBoardDataAPI.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dashboard_widget);


        views.setTextViewText(R.id.txt, txt);

        loginAPI.getDashBoardData("Token " + token, "4", employeeID).enqueue(
                new Callback<DashBoardApiResponse>() {

                    @Override

                    public void onResponse(Call<DashBoardApiResponse> call, Response<DashBoardApiResponse> response) {

                        if (response.isSuccessful())

                            if (response.body().getSuccess().equals("1") && response.body().getDashboard() != null) {

                                //dashboardMutableLiveData.setValue(response.body().getDashboard());

                                SharedPreferences.Editor editor = dashboardPref.edit();
                                editor.putString("TotalBookings", response.body().getDashboard().get(0).
                                        getTotalBookingsCount().toString());
                                editor.putString("paTaxi", response.body().getDashboard().get(0).
                                        getPaTaxiBookingsCount().toString());
                                editor.putString("paBus", response.body().getDashboard().get(0).
                                        getPaBusBookingsCount().toString());
                                editor.putString("paHotel", response.body().getDashboard().get(0).
                                        getPaHotelBookingsCount().toString());
                                editor.putString("paTrain", response.body().getDashboard().get(0).
                                        getPaTrainBookingsCount().toString());
                                editor.putString("paFlight", response.body().getDashboard().get(0).
                                        getPaFlightBookingsCount().toString());
                                editor.commit();
                                Log.d("Widget TAG",
                                        response.body().getDashboard().get(0).getTotalBookingsCount().toString() +
                                                response.body().getDashboard().get(0).getPaTaxiBookingsCount().toString() +
                                                response.body().getDashboard().get(0).getPaBusBookingsCount().toString() +
                                                response.body().getDashboard().get(0).getPaHotelBookingsCount().toString() +
                                                response.body().getDashboard().get(0).getPaTrainBookingsCount().toString() +
                                                response.body().getDashboard().get(0).getPaFlightBookingsCount().toString());
                            } else {
                                Log.e("Widget TAG", "Connection Unsuccessful");
                                SharedPreferences.Editor editor = dashboardPref.edit();
                                editor.putString("TotalBookings", "0");
                                editor.putString("paTaxi", "0");
                                editor.putString("paBus", "0");
                                editor.putString("paHotel", "0");
                                editor.putString("paTrain", "0");
                                editor.putString("paFlight", "0");
                                editor.commit();
                            }
                    }

                    @Override
                    public void onFailure(Call<DashBoardApiResponse> call, Throwable t) {

                    }
                }
        );


        // Set the text
        views.setTextViewText(R.id.total, dashboardPref.getString("TotalBookings", "0"));
        views.setTextViewText(R.id.pa_taxi, dashboardPref.getString("paTaxi", "0"));
        views.setTextViewText(R.id.pa_bus, dashboardPref.getString("paBus", "0"));
        views.setTextViewText(R.id.pa_hotel, dashboardPref.getString("paHotel", "0"));
        views.setTextViewText(R.id.pa_train, dashboardPref.getString("paTrain", "0"));
        views.setTextViewText(R.id.pa_flight, dashboardPref.getString("paFlight", "0"));
        views.setTextViewText(R.id.view, "");
        // Instruct the widget manager to update the widget
        Intent intentUpdate = new Intent(context, DashboardWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.total, pendingUpdate);
        Intent intent = new Intent(context, DashboardWidget.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.total, pendingIntent);


        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.pa_taxi, pendingIntent);
        views.setOnClickPendingIntent(R.id.widget_lay, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Toast.makeText(context, "Widget has been updated! ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
     /*   if (ButtonClick1.equals(intent.getAction())) {
            //dashboardViewModel = ViewModelProviders.of(context).get(DashboardViewModel.class);

            Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button1");
        }*/
    }
}

