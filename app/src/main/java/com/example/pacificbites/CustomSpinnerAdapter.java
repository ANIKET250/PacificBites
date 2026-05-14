package com.example.pacificbites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<FoodItem> {

    private Context context;
    private List<FoodItem> foodItems;
    private boolean isFirstItem = false;

    public CustomSpinnerAdapter(Context context, List<FoodItem> foodItems) {
        super(context, 0, foodItems);
        this.context = context;
        this.foodItems = foodItems;
    }

    public void setFirstItem(boolean isFirst) {
        isFirstItem = isFirst;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (isFirstItem && position == 0) {
            // For the selected view (when closed), show "Please select"
            View view = LayoutInflater.from(context).inflate(R.layout.spinner_selected_item, parent, false);
            TextView textView = view.findViewById(R.id.spinnerSelectedText);
            textView.setText("📋 Please select a food item");
            textView.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
            return view;
        }
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, parent, false);
        }

        ImageView iconView = convertView.findViewById(R.id.foodIcon);
        TextView nameView = convertView.findViewById(R.id.foodName);
        TextView priceView = convertView.findViewById(R.id.foodPrice);

        FoodItem item = foodItems.get(position);

        iconView.setImageResource(item.getIcon());
        nameView.setText(item.getName());
        priceView.setText(item.getPrice());

        return convertView;
    }
}