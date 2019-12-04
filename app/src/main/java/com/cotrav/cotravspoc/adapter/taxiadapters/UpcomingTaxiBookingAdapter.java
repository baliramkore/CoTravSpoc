package com.cotrav.cotravspoc.adapter.taxiadapters;

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
import com.cotrav.cotravspoc.activities.showbookings.ShowTaxiBooking;
import com.cotrav.cotravspoc.fragments.showtaxi.TaxiDetailsFragment;
import com.cotrav.cotravspoc.models.RejectBookingResponse;
import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.Passanger;
import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotravspoc.retrofit.ConfigRetrofit;
import com.cotrav.cotravspoc.retrofit.RejectBookingAPI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class UpcomingTaxiBookingAdapter extends RecyclerView.Adapter<UpcomingTaxiBookingAdapter.MyViewHolder> {

    private FragmentActivity activity;
    private List<TaxiBooking> taxiBookingList;
    static String bookingStatus,strAuth,struserId,strbookingId,struserType;
    Passanger taxiPassanger;
    Context context;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public UpcomingTaxiBookingAdapter(FragmentActivity activity, List<TaxiBooking> taxiBookingList, String bookingStatus,String strAuth,String struserType,String struserId) {
        this.activity = activity;
        this.taxiBookingList = taxiBookingList;
        this.strAuth=strAuth;
        this.struserId=struserId;
        this.struserType=struserType;
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
        Log.d("Taxi info", GsonStringConvertor.gsonToString(taxiBookingList.get(position)));


        if (taxiBookingList.get(position).getPassangers().size()<=0){

            viewHolder.bookingSpocName.setText("No Emp");
        }else
        {
            for (int i=0;i<=taxiBookingList.get(position).getPassangers().size();i++)
            {

                taxiPassanger= taxiBookingList.get(position).getPassangers().get(0);


            }
            viewHolder.bookingSpocName.setText(taxiPassanger.getEmployeeName());


        }
        viewHolder.bookingId.setText(taxiBookingList.get(position).getReferenceNo());
        viewHolder.bookingTourType.setText(taxiBookingList.get(position).getTourType());
        viewHolder.bookingDateTime.setText(taxiBookingList.get(position).getBookingDate());

        if (taxiBookingList.get(position).getTourType().equals("1"))
            viewHolder.bookingTourType.setText("Radio");
        if (taxiBookingList.get(position).getTourType().equals("2"))
            viewHolder.bookingTourType.setText("Local");
        if (taxiBookingList.get(position).getTourType().equals("3"))
            viewHolder.bookingTourType.setText("OutStation");

        String pickupstr = taxiBookingList.get(position).getPickupLocation();
        String[] pickupSplit = pickupstr.split(", ");
        viewHolder.bookingPickupCity.setText(pickupSplit[0]);

        String dropstr = taxiBookingList.get(position).getDropLocation();
        String[] dropSplit = dropstr.split(", ");
        viewHolder.bookingDropCity.setText(dropSplit[0]);
        String bookingDate=taxiBookingList.get(position).getPickupDatetime();
        try {
            Date date1 = df.parse(bookingDate);
            viewHolder.bookingPickupDate.setText(dateFormat.format(date1));
            viewHolder.bookingPickupTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.bookingBoardingPoint.setText(taxiBookingList.get(position).getPickupLocation());
        viewHolder.bookingDropPoint.setText(taxiBookingList.get(position).getDropLocation());
        String dropDate=taxiBookingList.get(position).getBookingDate();
        try {
            Date date11 = df.parse(dropDate);
            viewHolder.bookingDateTime.setText(dateFormat.format(date11)+" "+timeFormat.format(date11));

        } catch (ParseException e) {
            e.printStackTrace();

        }

        viewHolder.bookingBoardingPoint.setText(taxiBookingList.get(position).getPickupLocation());
        viewHolder.bookingDropPoint.setText(taxiBookingList.get(position).getDropLocation());

        if (taxiBookingList.get(position).getStatusCotrav()<=1){
            viewHolder.bookingStatus.setText(taxiBookingList.get(position).getClientStatus());
            viewHolder.bookingDropDate.setText("Not Assigned");
        }
        else {
            if (taxiBookingList.get(position).getStatusCotrav() == 3||taxiBookingList.get(position).getStatusCotrav() == 2)
                viewHolder.bookingDropDate.setText("Not Assigned");

            viewHolder.bookingStatus.setText(taxiBookingList.get(position).getCotravStatus());
        }
        if (taxiBookingList.get(position).getStatusCotrav()>=4){

            try {
                Date date1 = viewHolder.df.parse(taxiBookingList.get(position).getPickupDatetime());
                viewHolder.bookingDropDate.setText(viewHolder.dateFormat.format(date1));
                viewHolder.bookingDropTime.setText(viewHolder.timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return taxiBookingList.size();
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
        private TextView bookingTourType;
        private TextView bookingDateTime;

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

            bookingDateTime=itemView.findViewById(R.id.booking_date);
            bookingTourType=itemView.findViewById(R.id.tour_type);
            bookingTourType.setVisibility(View.VISIBLE);


            bookingPickupCity = itemView.findViewById(R.id.pickup_city);
            bookingDropCity = itemView.findViewById(R.id.drop_city);

            bookingPickupDate = itemView.findViewById(R.id.pickup_date);
            bookingPickupTime = itemView.findViewById(R.id.pickup_time);

            bookingDropDate = itemView.findViewById(R.id.drop_date);
            bookingDropTime = itemView.findViewById(R.id.drop_time);

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
        public void onClick(View view)
        {

            int id=view.getId();
            final int position=getAdapterPosition();
            switch (id){
                case R.id.imageDetails:
                    //Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    TaxiDetailsFragment detailBookingFragment = new TaxiDetailsFragment();
                    Bundle args=new Bundle();
                   // args.putString("details", GsonStringConvertor.gsonToString(taxiBookingList.get(position)));
                    args.putString("bookingId",taxiBookingList.get(position).getId().toString());
                    args.putString("booking_status","Cancelled");
                    detailBookingFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pager_container, detailBookingFragment).
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
                        alertDialogBuilder.setMessage("Are you sure you want to call " + taxiBookingList.get(position).getUserName() + " ?");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(Intent.ACTION_CALL,
                                        Uri.parse("tel:" + taxiBookingList.get(position).getUserContact())));
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

                            strbookingId= String.valueOf(taxiBookingList.get(position).getId());
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
            rejectBookingAPI.rejectTaxiBooking(strAuth,struserType,strbookingId,struserId).enqueue(new Callback<RejectBookingResponse>()
            {
                @Override
                public void onResponse(Call<RejectBookingResponse> call, retrofit2.Response<RejectBookingResponse> response) {

                    if(response.code()==200)
                    {
                        assesmentvalue=response.body().toString();

                        Toast.makeText(context,"Booking rejected :"+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, ShowTaxiBooking.class);
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
