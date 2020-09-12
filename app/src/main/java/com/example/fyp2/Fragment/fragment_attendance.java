package com.example.fyp2.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.fyp2.Adapter.AttendancesListAdapter;
import com.example.fyp2.Adapter.BuyerOrderHistoryListAdapter;
import com.example.fyp2.BackEndServer.RetrofitClient;
import com.example.fyp2.Class.Attendance;
import com.example.fyp2.Class.OTP;
import com.example.fyp2.Class.Order;
import com.example.fyp2.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_attendance extends Fragment {
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private Button button;
    private BarcodeDetector barcodeDetector;
    final int RequestCameraPermissionID = 1001;
    private FloatingActionButton adminCheck;
    private View v;
    private String userIc;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private ListView attendanceList;
    ArrayList<Attendance> attendancesArrayList;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_attendance, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("BOM_PREFS", MODE_PRIVATE);
        userIc = sharedPreferences.getString("USERIC", "");

        attendancesArrayList = new ArrayList<>();
        surfaceView = (SurfaceView) v.findViewById(R.id.Camera_attendance);
        button = (Button) v.findViewById(R.id.Request_attendance);
        adminCheck = (FloatingActionButton) v.findViewById(R.id.fragment_attendance_admin_check);

        button.setOnClickListener(q -> {
            Attendance attendance = new Attendance(userIc);
            requestAttendance(attendance, getContext());
        });
        barcodeDetector = new BarcodeDetector.Builder(getActivity()).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector).setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {
                        SparseArray<Barcode> qrCodes = detections.getDetectedItems();
//                        textView.setText(qrCodes.valueAt(0).displayValue);
                        decryptData(qrCodes.valueAt(0).displayValue, getContext());
                    }
                });
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        adminCheck.setOnClickListener(q -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = getLayoutInflater().inflate(R.layout.dialog_attendance_list, null);
            Button morning = view.findViewById(R.id.fragment_attendance_morning);
            Button evening = view.findViewById(R.id.fragment_attendance_evening);
            Button ot = view.findViewById(R.id.fragment_attendance_ot);
            attendanceList = view.findViewById(R.id.fragment_attendance_list_view);
            morning.setOnClickListener(w -> {
                getAttendance("morning", getContext());
            });
            evening.setOnClickListener(e -> {
                getAttendance("evening", getContext());
            });
            ot.setOnClickListener(r -> {
                getAttendance("ot", getContext());
            });

            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        });
        return v;
    }

    public void requestAttendance(Attendance attendance, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().requestAttendance(attendance);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Scan the QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Request Attendance Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void decryptData(String text, Context context) {
        String[] encryptData = text.split("/");
        String decryptData = "";
        for (String var : encryptData) {
            long x = Long.valueOf(var) - Long.valueOf(userIc);
            decryptData = decryptData + x;
        }
        OTP otp = new OTP(userIc, decryptData);
        checkAttendance(otp, getContext());
        Toast.makeText(context, decryptData, Toast.LENGTH_SHORT).show();
    }

    public void checkAttendance(OTP x, Context context) {
        Call<Void> call = RetrofitClient.getInstance().getApi().createAttendance(x);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Signed Attendance", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Sign Attendance Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAttendance(String text, Context context) {
        attendanceList.setAdapter(null);
        attendancesArrayList.clear();
        Call<List<Attendance>> call = RetrofitClient.getInstance().getApi().currentDayAttendance(text);
        call.enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                attendancesArrayList.clear();
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Get Order History Fail", Toast.LENGTH_LONG).show();
                } else {
                    List<Attendance> attendances = response.body();
                    for (Attendance current : attendances) {
                        attendancesArrayList.add(current);
                        //Toast.makeText(getContext(), current.getAttendanceID(), Toast.LENGTH_SHORT).show();
                    }
                    final AttendancesListAdapter attendancesList = new AttendancesListAdapter(getActivity(), R.layout.adapter_attendance_list, attendancesArrayList);
                    attendancesList.notifyDataSetChanged();
                    attendanceList.setAdapter(attendancesList);
                }
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}