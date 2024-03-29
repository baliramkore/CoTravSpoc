package com.cotrav.cotravspoc.adapter.trainadapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.fragments.showbus.BusDetailsFragment;
import com.cotrav.cotravspoc.fragments.showtrain.TrainDetailsFragment;
import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainBooking;
import com.cotrav.cotravspoc.models.show_train_model.show_train.TrainPassanger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CancelledTrainBookingAdapter extends RecyclerView.Adapter<CancelledTrainBookingAdapter.MyViewHolder>
{

    private FragmentActivity activity;
    private List<TrainBooking> trainBookingList;
    private String bookingStatus;
    Context context;
    TrainPassanger trainPassanger;

    public CancelledTrainBookingAdapter(FragmentActivity activity, List<TrainBooking> trainBookingList, String bookingStatus) {
        this.activity = activity;
        this.trainBookingList = trainBookingList;
        this.bookingStatus = bookingStatus;
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
        //viewHolder.bookingSpocName.setText(trainBookingList.get(position).getUserName());
        //viewHolder.bookingPickupCity.setText(trainBookingList.get(position).getPickupLocation());
        //viewHolder.bookingDropCity.setText(trainBookingList.get(position).getDropLocation());
        viewHolder.bookingDate.setText(trainBookingList.get(position).getBoardingDatetime());


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
        try {
            Date date1 = df.parse(pickupDate);
            viewHolder.bookingPickupDate.setText(dateFormat.format(date1));
            viewHolder.bookingPickupTime.setText(timeFormat.format(date1));
            //viewHolder.bookingDropDate.setText(dateFormat.format(date1));
            //viewHolder.bookingDropTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
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
        private ImageView imageViewCall,imageViewWhatsApp,imageViewDetails,imageViewCancel;
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
            imageViewCall.setVisibility(View.INVISIBLE);

            //imageViewWhatsApp=(ImageView) itemView.findViewById(R.id.imv_whatsapp);
            //imageViewWhatsApp.setVisibility(View.INVISIBLE);
            //imageViewWhatsApp.setOnClickListener(this);

            imageViewCancel=(ImageView) itemView.findViewById(R.id.imv_cancel);
            imageViewCancel.setVisibility(View.INVISIBLE);
            //imageViewCancel.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {

            int id=view.getId();
            int position=getAdapterPosition();
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

            }

        }
    }
}
