package com.example.fyp2.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.BuyerListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Buyer;
import com.example.fyp2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class fragment_buyers extends Fragment {
    View v;
    FloatingActionButton filter, add;
    ArrayList<Buyer> buyerList;
    ListView buyerListView;
    String userIc, roles;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_buyers, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");
        roles = sharedPreferences.getString("ROLE", "");

        buyerList = new ArrayList<>();
        buyerListView = (ListView) v.findViewById(R.id.buyersList);
        getBuyerList(getContext());

        filter = (FloatingActionButton) v.findViewById(R.id.fragment_buyers_filter_btn);
        filter.setOnClickListener(e -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_buyer_filter, null);
            Spinner State = (Spinner) mView.findViewById(R.id.customer_category_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            State.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });

        add = (FloatingActionButton) v.findViewById(R.id.fragment_buyers_add_buyer);
        add.setOnClickListener(k -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_buyer_add_buyer, null);
            TextView name = (TextView) mView.findViewById(R.id.fragment_buyer_add_buyer_name);
            TextView contact = (TextView) mView.findViewById(R.id.fragment_buyer_add_buyer_contact);
            Spinner location = (Spinner) mView.findViewById(R.id.fragment_buyer_add_buyer_location_spinner);
            TextView address = (TextView) mView.findViewById(R.id.fragment_buyer_add_buyer_address);
            Button submit = (Button) mView.findViewById(R.id.Buyer_Add_Buyer_Btn);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.locations, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            location.setAdapter(adapter);
            mBuilder.setView(mView);
            AlertDialog dialogAdd = mBuilder.create();
            dialogAdd.show();
            //b3.getString("userIc")
            submit.setOnClickListener(e -> {
                Buyer addBuyer = new Buyer(name.getText().toString(), contact.getText().toString(), location.getSelectedItem().toString(), address.getText().toString(), userIc);
                addBuyer(addBuyer, getContext());
                dialogAdd.dismiss();
            });
        });
        buyerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getActivity(),buyerList.get(i).getBuyerName(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder mmBuilder = new AlertDialog.Builder(getActivity());
                View mmView = getLayoutInflater().inflate(R.layout.dialog_buyer_detail, null);
                TextView name = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_name);
                TextView contact = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_contact);
                TextView location = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_location);
                TextView address = (TextView) mmView.findViewById(R.id.fragment_buyer_detail_address);
                ImageView check = (ImageView) mmView.findViewById(R.id.buyer_detail_check_n_uncheck);
                Button confirmCheck = (Button) mmView.findViewById(R.id.buyer_detail_confirm_check);
                name.setText(buyerList.get(i).getBuyerName());
                contact.setText(buyerList.get(i).getBuyerContact());
                location.setText(buyerList.get(i).getBuyerLocation());
                address.setText(buyerList.get(i).getBuyerAddress());
                if (buyerList.get(i).getAdminCheck().equals("true")) {
                    check.setImageResource(R.drawable.icon_check);
                    confirmCheck.setVisibility(View.GONE);
                } else {
                    if (roles.equals("5")) {
                        confirmCheck.setVisibility(View.VISIBLE);
                    } else {
                        confirmCheck.setVisibility(View.GONE);
                    }
                }
                mmBuilder.setView(mmView);
                AlertDialog dialogDetail = mmBuilder.create();
                dialogDetail.show();
                confirmCheck.setOnClickListener(e -> {
                    AlertDialog.Builder mmBuilderr = new AlertDialog.Builder(getActivity());
                    View mmVieww = getLayoutInflater().inflate(R.layout.dialog_buyer_confirm_check, null);
                    Button checkYes = (Button) mmVieww.findViewById(R.id.buyer_Detail_check_yes);
                    Button checkNo = (Button) mmVieww.findViewById(R.id.buyer_Detail_check_no);
                    mmBuilderr.setView(mmVieww);
                    AlertDialog dialogCheck = mmBuilderr.create();
                    dialogCheck.show();
                    checkYes.setOnClickListener(k -> {
                        Buyer buyer = new Buyer(buyerList.get(i).getBuyerId(), userIc, "true");
                        adminCheck(buyer, getContext());
                        dialogCheck.dismiss();
                        dialogDetail.dismiss();
                        buyerList.clear();
                        getBuyerList(getContext());
                    });
                    checkNo.setOnClickListener(p -> {
                        dialogCheck.dismiss();
                    });
                });
            }
        });
        return v;
    }

    public void getBuyerList(Context context) {
        Call<List<Buyer>> call = RetrofitClient.getInstance().getApi().findAllBuyer();
        call.enqueue(new Callback<List<Buyer>>() {
            @Override
            public void onResponse(Call<List<Buyer>> call, Response<List<Buyer>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(context, "Get Buyers Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Buyer> buyers = response.body();
                    for (Buyer currentBuyer : buyers) {
                        buyerList.add(currentBuyer);
                    }
                    final BuyerListAdapter adapter = new BuyerListAdapter(getActivity(), R.layout.adapter_buyer_list, buyerList);
                    adapter.notifyDataSetChanged();
                    buyerListView.setAdapter(adapter);
                    //Toast.makeText(context,buyerList.get(0).getBuyerId(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Buyer>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addBuyer(Buyer buyer, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().addBuyer(buyer);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Add Buyer Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Add Buyer Fail", Toast.LENGTH_LONG).show();
                }
                buyerList.clear();
                getBuyerList(getContext());
                //getActivity().getSupportFragmentManager().beginTransaction().replace(fragment_buyers.this.getId(), new fragment_buyers()).commit();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void adminCheck(Buyer buyer, Context context) {
        Call<Buyer> call = RetrofitClient.getInstance().getApi().checkBuyer(buyer);
        call.enqueue(new Callback<Buyer>() {
            @Override
            public void onResponse(Call<Buyer> call, Response<Buyer> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Checked Buyer", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Check Buyer Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Buyer> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }


}