package kidsuke.shoppingapp.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kidsuke.shoppingapp.R;
import kidsuke.shoppingapp.model.Product;

/**
 * Created by ADMIN on 02-Feb-17.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private List<Product> productList;

    public MainAdapter(Activity context, List<Product> productList){
        this.context = context;
        this.productList = productList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.main_cardview, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Init code view
        TextView code = ((MainViewHolder) holder).getCodeTextView();
        code.setText(productList.get(position).getProductCode());
        //Init name view
        TextView name = ((MainViewHolder) holder).getNameTextView();
        name.setText(productList.get(position).getProductName());
        //Init price view
        TextView price = ((MainViewHolder) holder).getPriceTextView();
        price.setText(productList.get(position).getRetailPrice());
        //Init count view
        TextView count = ((MainViewHolder) holder).getCountTextView();
        count.setText(productList.get(position).getCount());
        //Init count button
        ImageButton add = ((MainViewHolder) holder).getAddButton();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.get(position).increaseCount();
                notifyDataSetChanged();
            }
        });
        ImageButton minus = ((MainViewHolder) holder).getMinusButton();
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.get(position).decreaseCount();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView code, name, price, count;
        private ImageButton add, minus;

        MainViewHolder(View base){
            super(base);
            code   = (TextView) base.findViewById(R.id.code);
            name   = (TextView) base.findViewById(R.id.name);
            price  = (TextView) base.findViewById(R.id.price);
            count  = (TextView) base.findViewById(R.id.count);
            add = (ImageButton) base.findViewById(R.id.count_button);
            minus = (ImageButton) base.findViewById(R.id.remove_button);
        }

        TextView getCodeTextView(){
            return code;
        }

        TextView getNameTextView(){
            return name;
        }

        TextView getPriceTextView(){
            return price;
        }

        TextView getCountTextView(){
            return count;
        }

        ImageButton getAddButton(){
            return add;
        }

        ImageButton getMinusButton() {
            return minus;
        }
    }

    public void updateList(List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }
}
