package com.cotrav.cotravspoc.adapter.trainadapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.cotrav.cotravspoc.activities.showbookings.ShowFlightBooking;
import com.cotrav.cotravspoc.activities.showbookings.ShowTrainBooking;
import com.cotrav.cotravspoc.fragments.showtrain.TrainDetailsFragment;
import com.cotrav.cotravspoc.models.RejectBookingResponse;
import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainBooking;
import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainPassanger;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.RejectBookingAPI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class UpcomingTrainBookingAdapter extends RecyclerView.Adapter<UpcomingTrainBookingAdapter.MyViewHolder> {

    private FragmentActivity activity;
    private List<TrainBooking> trainBookingList;
    static String bookingStatus,strAuth,struserId,strbookingId,struserType;
    Context context;
    TrainPassanger trainPassanger;
    public UpcomingTrainBookingAdapter(FragmentActivity activity, List<TrainBooking> trainBookingList, String bookingStatus,
                                       String strAuth,String struserId,String struserType) {
        this.activity = activity;
        this.trainBookingList = trainBookingList;
        this.bookingStatus = bookingStatus;
        this.strAuth=strAuth;
        this.struserType=struserType;
        this.struserId=struserId;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_booking_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position)
    {
        Log.d("Taxi info", GsonStringConvertor.gsonToString(trainBookingList.get(position)));

        if (trainBookingList.get(position).getPassangers().size()<=0){

            viewHolder.bookingSpocName.setText("No Emp");
        }else
        {
            for (int i=0;i<=trainBookingList.get(position).getPassangers().size();i++)
            {

                trainPassanger= trainBookingList.get(position).getPassangers().get(0);


            }
            viewHolder.bookingSpocName.setText(trainPassanger.getEmployeeName());


        }
        viewHolder.bookingId.setText(trainBookingList.get(position).getReferenceNo());
        viewHolder.bookingStatus.setText(trainBookingList.get(position).getCotravStatus());
       // viewHolder.bookingSpocName.setText(trainBookingList.get(position).getUserName());
        viewHolder.bookingDate.setText(trainBookingList.get(position).getBookingDatetime());

/*
        String pickupstr = trainBookingList.get(position).getPickupLocation();
        String[] pickupSplit = pickupstr.split(", ");
        viewHolder.bookingPickupCity.setText(pickupSplit[0]);

        String dropstr = trainBookingList.get(position).getDropLocation();
        String[] dropSplit = dropstr.split(", ");
        viewHolder.bookingDropCity.setText(dropSplit[0]);*/

        String pickupstr = trainBookingList.get(position).getPickupLocation();
        String dropstr = trainBookingList.get(position).getDropLocation();
        if (pickupstr!=null&&dropstr!=null) {

            String[] pickupSplit = pickupstr.split(" ");
            viewHolder.bookingPickupCity.setText(pickupSplit[0]);

            String[] dropSplit = dropstr.split(" ");
            viewHolder.bookingDropCity.setText(dropSplit[0]);

        }
        String pickupDate=trainBookingList.get(position).getPickupFromDatetime();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try
        {
            Date date1 = df.parse(pickupDate);
            viewHolder.bookingPickupDate.setText(dateFormat.format(date1));
            viewHolder.bookingPickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        String dropDate=trainBookingList.get(position).getBookingDatetime();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM yyyy");
        DateFormat timeFormat1 = new SimpleDateFormat("HH:mm");
        try {
            Date date11 = df1.parse(dropDate);

            viewHolder.bookingDate.setText(dateFormat1.format(date11)+" "+timeFormat1.format(date11));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.bookingBoardingPoint.setText(trainBookingList.get(position).getPickupLocation());
        viewHolder.bookingDropPoint.setText(trainBookingList.get(position).getDropLocation());

        if (trainBookingList.get(position).getStatusCotrav()<=1){
            viewHolder.bookingStatus.setText(trainBookingList.get(position).getClientStatus());
            viewHolder.bookingDropDate.setText("Not Assigned");
        }
        else {
            if (trainBookingList.get(position).getStatusCotrav() == 3||trainBookingList.get(position).getStatusCotrav() == 2)
                viewHolder.bookingDropDate.setText("Not Assigned");

            viewHolder.bookingStatus.setText(trainBookingList.get(position).getCotravStatus());
        }
        if (trainBookingList.get(position).getStatusCotrav()>=4){

            try {
                Date date1 = viewHolder.df.parse(trainBookingList.get(position).getPickupToDatetime());
                viewHolder.bookingDropDate.setText(viewHolder.dateFormat.format(date1));
                viewHolder.bookingDropTime.setText(viewHolder.timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return trainBookingList.size();
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
        private ImageView imageViewCall,imageViewDetails,imageViewCancel;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");




        public MyViewHolder(@NonNull View itemView)
        {
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

            imageViewCancel=(ImageView) itemView.findViewById(R.id.imv_cancel);
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
                    TrainDetailsFragment detailBusBookingFragment = new TrainDetailsFragment();
                    Bundle args=new Bundle();
                    args.putString("details", GsonStringConvertor.gsonToString(trainBookingList.get(position)));
                    args.putString("bookingId",trainBookingList.get(position).getId().toString());
                    args.putString("booking_status","Cancelled");
                    detailBusBookingFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pager_container, detailBusBookingFragment).
                            addToBackStack(null).commit();
                    break;

                case R.id.imv_call:
                    //Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.getPackageName(), null)));
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("Make a Call");
                        alertDialogBuilder.setIcon(R.drawable.phonecall);
                        alertDialogBuilder.setMessage("Are you sure you want to call " + trainBookingList.get(position).getUserName() + " ?");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(Intent.ACTION_CALL,
                                        Uri.parse("tel:" + trainBookingList.get(position).getUserContact())));
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

                            strbookingId=String.valueOf(trainBookingList.get(position).getId());
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
            rejectBookingAPI.rejectTrainBooking(strAuth,struserType,strbookingId,struserId).enqueue(new Callback<RejectBookingResponse>()
            {
                @Override
                public void onResponse(Call<RejectBookingResponse> call, retrofit2.Response<RejectBookingResponse> response) {

                    if(response.code()==200)
                    {
                        assesmentvalue=response.body().toString();

                        Toast.makeText(context,"Booking rejected :"+response.body().getMessage(),Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(context, ShowTrainBooking.class);
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
