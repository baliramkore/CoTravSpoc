package com.cotrav.cotravspoc.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.cotrav.cotravspoc.GsonStringConvertor;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;

import java.util.List;

public class TaxiBookingAdapter extends RecyclerView.Adapter<TaxiBookingAdapter.MyViewHolder> {
    private FragmentActivity activity;
    private List<TaxiBooking> taxiBookingList;
    private String bookingStatus;


    public TaxiBookingAdapter(FragmentActivity activity, List<TaxiBooking> taxiBookingList, String bookingStatus) {
        this.activity=activity;
        this.taxiBookingList=taxiBookingList;
        this.bookingStatus=bookingStatus;
    }


    @Override
    public void onBindViewHolder(@NonNull TaxiBookingAdapter.MyViewHolder viewHolder, int position) {
        Log.d("Taxi info", GsonStringConvertor.gsonToString(taxiBookingList.get(position)));
        //viewHolder.bookingIdTextView.setText(taxiBookingList.get(position).getReferenceNo());
       // viewHolder.spocNameTextView.setText(taxiBookingList.get(position).userName);
       // viewHolder.fromCity.setText(taxiBookingList.get(position).getCityName());
        //viewHolder.departureDate.setText(taxiBookingList.get(position).getPickupDatetime());
        //viewHolder.companyStatusTextView.setText(taxiBookingList.get(position).getBookingStatus());
        //viewHolder.toCity.setText(taxiBookingList.get(position).getCityName());
        //viewHolder.dropLocation.setText(taxiBookingList.get(position).getDropLocation());
        //viewHolder.pickupLocation.setText(taxiBookingList.get(position).getPickupLocation());

    }



    @NonNull
    @Override
    public TaxiBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_taxi_booking, parent, false);
        return new MyViewHolder(itemView);
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



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingId = itemView.findViewById(R.id.refrence_no);
            bookingStatus = itemView.findViewById(R.id.booking_status);
            bookingSpocName = itemView.findViewById(R.id.spoc_name);




        }

        @Override
        public void onClick(View view) {

        }
    }
}
