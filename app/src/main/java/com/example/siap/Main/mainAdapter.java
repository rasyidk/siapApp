package com.example.siap.Main;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siap.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class mainAdapter extends RecyclerView.Adapter implements Filterable {

    List<mainModel> moviesList;
    List<mainModel> moviesListAll;

    public mainAdapter(List<mainModel> moviesList) {
        this.moviesList = moviesList;
        this.moviesListAll = new ArrayList<>(moviesList);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        view = layoutInflater.inflate(R.layout.main_row, parent, false);
        return new ViewHolderTwo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;

//        viewHolderTwo.textView.setText(moviesList.get(position).getName());

        viewHolderTwo.tv_noreg.setText(moviesList.get(position).getNoreg());
        viewHolderTwo.tv_pangkatnm.setText(moviesList.get(position).getPangkatnm());
        viewHolderTwo.tv_satuan.setText(moviesList.get(position).getSatuan());
        viewHolderTwo.tv_pasal.setText(moviesList.get(position).getPasal());
        viewHolderTwo.tv_noputusan.setText(moviesList.get(position).getNoputusan());
        viewHolderTwo.tv_noeksekusi.setText(moviesList.get(position).getNoeksekusi());
        viewHolderTwo.tv_tglarsip.setText(moviesList.get(position).getTglarsip());
        viewHolderTwo.tv_norak.setText(moviesList.get(position).getNorak());
        viewHolderTwo.tv_noarsip.setText(moviesList.get(position).getNoarsip());

        viewHolderTwo.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), moviesList.get(position).getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse( moviesList.get(position).getUrl()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public Filter getFilter() {
        return FilterUser;
    }

    Filter FilterUser =  new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<mainModel> templist= new ArrayList<>();
            if (searchText.length() == 0 || searchText.isEmpty()){
                templist.addAll(moviesListAll);
            }else {
                for (mainModel item : moviesListAll){
                    if (item.getName().contains(searchText)){
                        templist.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = templist;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            moviesList.clear();
            moviesList.addAll((Collection<? extends mainModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolderTwo extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView tv_noreg,tv_pangkatnm,tv_satuan,tv_pasal,tv_noputusan,tv_noeksekusi,tv_tglarsip, tv_noarsip, tv_norak;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            tv_noreg = itemView.findViewById(R.id.main_row_noreg);
            tv_pangkatnm = itemView.findViewById(R.id.main_row_pangkatnm);
            tv_satuan = itemView.findViewById(R.id.main_row_satuan);
            tv_pasal = itemView.findViewById(R.id.main_row_pasal);
            tv_noputusan =  itemView.findViewById(R.id.main_row_noputusan);
            tv_noeksekusi = itemView.findViewById(R.id.main_row_noeksekusi);
            tv_tglarsip =  itemView.findViewById(R.id.main_row_tglarsip);
            tv_norak =  itemView.findViewById(R.id.main_row_norak);
            tv_noarsip = itemView.findViewById(R.id.main_row_noarsip);

            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
