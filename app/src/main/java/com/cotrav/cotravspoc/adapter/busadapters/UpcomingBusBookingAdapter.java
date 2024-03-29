package com.cotrav.cotravspoc.adapter.busadapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.activities.addbookings.addbus.AddBusBookingActivity;
import com.cotrav.cotravspoc.activities.showbookings.ShowBusBooking;
import com.cotrav.cotravspoc.activities.showbookings.ShowFlightBooking;
import com.cotrav.cotravspoc.fragments.showbus.BusDetailsFragment;
import com.cotrav.cotravspoc.models.RejectBookingResponse;
import com.cotrav.cotravspoc.models.show_bus_model.show_bus.BusBooking;
import com.cotrav.cotravspoc.models.show_bus_model.show_bus.ShowBusPassanger;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.RejectBookingAPI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class UpcomingBusBookingAdapter extends RecyclerView.Adapter<UpcomingBusBookingAdapter.MyViewHolder>
{

    private FragmentActivity activity;
    private List<BusBooking> busBookingList;
    static String bookingStatus,strAuth,struserId,strbookingId,struserType;
    SharedPreferences loginpref;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    Context context;
    ShowBusPassanger taxiPassanger;

    List<ShowBusPassanger> taxiPassangerList;

    public UpcomingBusBookingAdapter(FragmentActivity activity, List<BusBooking> busBookingList, String bookingStatus
            ,String strAuth,String struserType,String struserId) {
        this.activity = activity;
        this.busBookingList = busBookingList;
        this.bookingStatus = bookingStatus;
        this.strAuth=strAuth;
        this.struserId=struserId;
        this.struserType=struserType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_booking_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position)
    {

        Log.d("Taxi info", GsonStringConvertor.gsonToString(busBookingList.get(position)));

        if (busBookingList.get(position).getPassangers().size()<=0){

            viewHolder.bookingSpocName.setText("No Emp");
        }else
        {
            for (int i=0;i<=busBookingList.get(position).getPassangers().size();i++)
            {

                //taxiPassangerList.add((ShowBusPassanger) busBookingList.get(position).getPassangers());
                taxiPassanger= busBookingList.get(position).getPassangers().get(0);


            }
            viewHolder.bookingSpocName.setText(taxiPassanger.getEmployeeName());


        }
        viewHolder.bookingId.setText(busBookingList.get(position).getReferenceNo());
        viewHolder.bookingStatus.setText(busBookingList.get(position).getCotravStatus());
        viewHolder.bookingDate.setText(busBookingList.get(position).getBookingDatetime());

        String pickupstr = busBookingList.get(position).getPickupLocation();
        String[] pickupSplit = pickupstr.split(", ");
        viewHolder.bookingPickupCity.setText(pickupSplit[0]);

        String dropstr = busBookingList.get(position).getDropLocation();
        String[] dropSplit = dropstr.split(", ");
        viewHolder.bookingDropCity.setText(dropSplit[0]);

        String pickupDate=busBookingList.get(position).getPickupFromDatetime();

        try {
            Date date1 = df.parse(pickupDate);
            viewHolder.bookingPickupDate.setText(dateFormat.format(date1));
            viewHolder.bookingPickupTime.setText(timeFormat.format(date1));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.bookingBoardingPoint.setText(busBookingList.get(position).getPickupLocation());
        viewHolder.bookingDropPoint.setText(busBookingList.get(position).getDropLocation());


         String dropDate=busBookingList.get(position).getPickupToDatetime();

        try {
            Date date11 = df.parse(dropDate);
            viewHolder.bookingDate.setText(dateFormat.format(date11)+" "+timeFormat.format(date11));
            //viewHolder.bookingDropTime.setText(timeFormat1.format(date11));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (busBookingList.get(position).getStatusCotrav()<=1){
            viewHolder.bookingStatus.setText(busBookingList.get(position).getClientStatus());
            viewHolder.bookingDropDate.setText("Not Assigned");
        }
        else {
            if (busBookingList.get(position).getStatusCotrav() == 3||busBookingList.get(position).getStatusCotrav() == 2)
                viewHolder.bookingDropDate.setText("Not Assigned");

            viewHolder.bookingStatus.setText(busBookingList.get(position).getCotravStatus());
        }
        if (busBookingList.get(position).getStatusCotrav()>=4){

            try {
                Date date1 = viewHolder.df.parse(busBookingList.get(position).getPickupToDatetime());
                viewHolder.bookingDropDate.setText(viewHolder.dateFormat.format(date1));
                viewHolder.bookingDropTime.setText(viewHolder.timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return busBookingList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bookingId;
        private TextView bookingStatus;
        private TextView bookingSpocName;
        private TextView bookingPickupCity;
        private TextView bookingDropCity;
        private TextView bookingPickupTime;
        private TextView bookingPickupDate;
        private TextView bookingDropTime;
        private TextView bookingDropDate;
        private TextView bookingBoardingPoint;
        private TextView bookingDropPoint;
        private TextView bookingDate;
        private Button btnDetails;

        private ImageView imageViewCall,imageViewCancel,imageViewDetails;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingId = itemView.findViewById(R.id.refrence_no);
            bookingStatus = itemView.findViewById(R.id.booking_status);
            bookingSpocName = itemView.findViewById(R.id.spoc_name);
            bookingDate=itemView.findViewById(R.id.booking_date);
            bookingPickupCity = itemView.findViewById(R.id.pickup_city);
            bookingDropCity = itemView.findViewById(R.id.drop_city);
            bookingPickupTime = itemView.findViewById(R.id.pickup_time);
            bookingDropTime = itemView.findViewById(R.id.drop_time);
            bookingPickupDate = itemView.findViewById(R.id.pickup_date);
            bookingDropDate = itemView.findViewById(R.id.drop_date);
            bookingBoardingPoint = itemView.findViewById(R.id.pickup_boardingpoint);
            bookingDropPoint = itemView.findViewById(R.id.drop_droping_point);


            imageViewDetails=(ImageView) itemView.findViewById(R.id.imageDetails);
            imageViewDetails.setOnClickListener(this);

            imageViewCall=(ImageView) itemView.findViewById(R.id.imv_call);
            imageViewCall.setVisibility(View.VISIBLE);
            imageViewCall.setOnClickListener(this);

            imageViewCancel=(ImageView)itemView.findViewById(R.id.imv_cancel);
            imageViewCancel.setVisibility(View.VISIBLE);
            imageViewCancel.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int id=view.getId();
            final int position=getAdapterPosition();
            switch (id){
                case R.id.imageDetails:
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    BusDetailsFragment detailBusBookingFragment = new BusDetailsFragment();
                    Bundle args=new Bundle();
                    args.putString("details", GsonStringConvertor.gsonToString(busBookingList.get(position)));
                    args.putString("bookingId",busBookingList.get(position).getId());
                    args.putString("booking_status","Past");
                    detailBusBookingFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pager_container, detailBusBookingFragment).
                            addToBackStack(null).commit();
                    break;

                case R.id.imv_call:

                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();


                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.getPackageName(), null)));
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("Make a Call");
                        alertDialogBuilder.setIcon(R.drawable.phonecall);
                        alertDialogBuilder.setMessage("Are you sure you want to call " + busBookingList.get(position).getUserName() + " ?");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(Intent.ACTION_CALL,
                                        Uri.parse("tel:" + busBookingList.get(position).getUserName())));
                            }
                        });
                        alertDialogBuilder.setNegativeButton(
                                "No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = alertDialogBuilder.create();
                        alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                        alert11.show();
                    }

                    break;

                case R.id.imv_cancel:
                    Toast.makeText(context, "Cancell Button Clicked", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                    alertDialogBuilder.setTitle("CANCEL BOOKING");
                    alertDialogBuilder.setIcon(R.drawable.check_icon);
                    alertDialogBuilder.setMessage("Press Yes To Cancel Booking");
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @SuppressLint("MissingPermission")
                        public void onClick(DialogInterface dialog, int id) {

                            strbookingId=String.valueOf(busBookingList.get(position).getId());

                            new CancelBooking().execute(strAuth,struserType, strbookingId,struserId);

                        }
                    });
                    alertDialogBuilder.setNegativeButton(
                            "No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = alertDialogBuilder.create();
                    alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                    alert11.show();

                    break;
            }

        }
    }

    protected  class CancelBooking extends AsyncTask<String,Integer,String> {
        RejectBookingAPI rejectBookingAPI;
        private String assesmentvalue;

        @Override
        protected String doInBackground(String... params) {
            String Auth=params[0].toString();
            String usertype=params[1].toString();

            final AlertDialog.Builder[] d = new AlertDialog.Builder[1];

            Log.d("posting data",params.toString());
            rejectBookingAPI = ConfigRetrofit.configRetrofit(RejectBookingAPI.class);
            rejectBookingAPI.rejectBusBooking(strAuth,struserType,strbookingId,struserId).enqueue(new Callback<RejectBookingResponse>()
            {
                @Override
                public void onResponse(Call<RejectBookingResponse> call, retrofit2.Response<RejectBookingResponse> response) {

                    if(response.code()==200)
                    {
                        assesmentvalue=response.body().toString();

                        Toast.makeText(context,"Booking rejected :"+response.body().getMessage(),Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(context, ShowBusBooking.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }

                }

                @Override
                public void onFailure(Call<RejectBookingResponse> call, Throwable t) {

                }
            });
            return assesmentvalue;

        }

        ProgressDialog pd;
        android.app.AlertDialog.Builder d;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("sucess","Done");
            pd = new ProgressDialog(context);
            pd.setMessage("Reject booking process");
            d=new android.app.AlertDialog.Builder(context);
            d.setTitle("REJECT BOOKING");
            pd.show();
        }


        @Override
        protected void onPostExecute(String s)
        {

            pd.dismiss();

        }
    }
}
