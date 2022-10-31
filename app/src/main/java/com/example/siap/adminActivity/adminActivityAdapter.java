package com.example.siap.adminActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siap.Main.mainAdapter;
import com.example.siap.Main.mainModel;
import com.example.siap.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class adminActivityAdapter extends RecyclerView.Adapter implements Filterable {

    List<adminActivityModel> moviesList;
    List<adminActivityModel> moviesListAll;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    public adminActivityAdapter(List<adminActivityModel> moviesList) {
        this.moviesList = moviesList;
        this.moviesListAll = new ArrayList<>(moviesList);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        view = layoutInflater.inflate(R.layout.adminactivity_row, parent, false);
        return new ViewHolderTwo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;

        viewHolderTwo.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse( moviesList.get(position).getUrl()));
                view.getContext().startActivity(intent);
            }
        });

        viewHolderTwo.tv_noreg.setText(moviesList.get(position).getNoreg());
        viewHolderTwo.tv_pangkatnm.setText(moviesList.get(position).getPangkatnm());
        viewHolderTwo.tv_satuan.setText(moviesList.get(position).getSatuan());
        viewHolderTwo.tv_pasal.setText(moviesList.get(position).getPasal());
        viewHolderTwo.tv_noputusan.setText(moviesList.get(position).getNoputusan());
        viewHolderTwo.tv_noeksekusi.setText(moviesList.get(position).getNoeksekusi());
        viewHolderTwo.tv_tglarsip.setText(moviesList.get(position).getTglarsip());
        viewHolderTwo.tv_norak.setText(moviesList.get(position).getNorak());
        viewHolderTwo.tv_noarsip.setText(moviesList.get(position).getNoarsip());

        viewHolderTwo.adminact_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), moviesList.get(position).getKey(),Toast.LENGTH_LONG).show();
                String key =  moviesList.get(position).getKey();
                String url =  moviesList.get(position).getUrl();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Konfirmasi Hapus Data?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        final ProgressDialog progressDialog =  new ProgressDialog(view.getContext());
                        progressDialog.setTitle("Deleting Data");
                        progressDialog.show();

                        // Do nothing but close the dialog
                        databaseReference = FirebaseDatabase.getInstance().getReference("arsipDataBaru2");
                        databaseReference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                        Log.e("firebasestorage", "onSuccess: deleted file");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                        Log.e("firebasestorage", "onFailure: did not delete file");
                                    }
                                });

                                progressDialog.dismiss();
                                Toast.makeText(view.getContext(),"suceesfully deleted", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(view.getContext(),"Terjadi Error", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tv_noreg,tv_pangkatnm,tv_satuan,tv_pasal,tv_noputusan,tv_noeksekusi,tv_tglarsip, tv_norak, tv_noarsip;
        Button adminact_delete;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.adminact_row_cardview);
            adminact_delete =  itemView.findViewById(R.id.adminact_delete);

            tv_noreg = itemView.findViewById(R.id.adminact_row_noreg);
            tv_pangkatnm = itemView.findViewById(R.id.adminact_row_pangkatnm);
            tv_satuan = itemView.findViewById(R.id.adminact_row_satuan);
            tv_pasal = itemView.findViewById(R.id.adminact_row_pasal);
            tv_noputusan =  itemView.findViewById(R.id.adminact_row_noputusan);
            tv_noeksekusi = itemView.findViewById(R.id.adminact_row_noeksekusi);
            tv_tglarsip =  itemView.findViewById(R.id.adminact_row_tglarsip);
            tv_noarsip  =itemView.findViewById(R.id.adminact_row_noarsip);
            tv_norak = itemView.findViewById(R.id.adminact_row_norak);
        }
    }

    @Override
    public Filter getFilter() {
        return FilterUser;
    }

    Filter FilterUser =  new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<adminActivityModel> templist= new ArrayList<>();
            if (searchText.length() == 0 || searchText.isEmpty()){
                templist.addAll(moviesListAll);
            }else {
                for (adminActivityModel item : moviesListAll){
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
            moviesList.addAll((Collection<? extends adminActivityModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
//    adminact_row_cardview
//            adminact_delete
//
//    baru
//
//            adminact_row_noreg
//    adminact_row_pangkatnm
//            adminact_row_satuan
//    adminact_row_pasal
//            adminact_row_noputusan
//    adminact_row_noeksekusi
//            adminact_row_tglarsip