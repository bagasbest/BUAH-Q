package com.buahq.buahq.bayar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.buahq.buahq.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private final ArrayList<PaymentModel> listTransaction = new ArrayList<>();
    public void setData(ArrayList<PaymentModel> items) {
        listTransaction.clear();
        listTransaction.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bind(listTransaction.get(position), listTransaction);
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout cv;
        View view13;
        TextView name, price, date, status;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            view13 = itemView.findViewById(R.id.view13);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
        }

        @SuppressLint("SetTextI18n")
        public void bind(PaymentModel model, ArrayList<PaymentModel> listTransaction) {
            NumberFormat formatter = new DecimalFormat("#,###");

            name.setText(model.getCustomerName());
            price.setText("Rp." + formatter.format(model.getPrice()));
            date.setText(model.getDate());
            status.setText(model.getStatus());

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), PaymentDetailActivity.class);
                    intent.putExtra(PaymentDetailActivity.EXTRA_PAYMENT, model);
                    itemView.getContext().startActivity(intent);
                }
            });

            view13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Konfirmasi Hapus Transaksi")
                            .setMessage("Apakah anda yakin ingin menghapus transaksi ini ?")
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setPositiveButton("YA", (dialogInterface, i) -> {
                                // hapus produk
                                deleteTransaction(model.getTransactionId());
                                listTransaction.remove(listTransaction.get(getLayoutPosition()));
                                notifyDataSetChanged();
                            })
                            .setNegativeButton("TIDAK", (dialog, i) -> {
                                dialog.dismiss();
                            })
                            .show();
                }
            });
        }

        private void deleteTransaction(String transactionId) {
            FirebaseFirestore
                    .getInstance()
                    .collection("transaction")
                    .document(transactionId)
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(itemView.getContext(), "Berhasil menghapus transaksi", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(itemView.getContext(), "Gagal menghapus transaksi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
