package id.kopas.berkarya.zakatku;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.kopas.berkarya.zakatku.utils.Fungsi;

public class ZakatProfesiFragment  extends Fragment {

    ProgressDialog pd;
    LinearLayout zakat_profesi_calc,zakat_profesi_result,zakat_profesi_tips;
    AppCompatButton zakatprofesi_hitung;
    TextView zakat_profesi_reset;
    TextInputEditText zakatprofesi_jumlahpenghasilan,zakatprofesi_hargaberas,zakatprofesi_tanggal;
    TextView isiBio1,isiBio2, isiBio3, zakat_profesi_nominal;
    public Fragment newInstance(int position, String title) {
        ZakatProfesiFragment f = new ZakatProfesiFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("ARG_PAGE", position);
        args.putString("ARG_TITLE", title);
        f.setArguments(args);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_zakatprofesi, container, false);
        zakat_profesi_calc = rootView.findViewById(R.id.zakat_profesi_calc);
        zakat_profesi_result = rootView.findViewById(R.id.zakat_profesi_result);
        zakat_profesi_tips = rootView.findViewById(R.id.zakat_profesi_tips);

        zakat_profesi_calc.setVisibility(View.VISIBLE);
        zakat_profesi_result.setVisibility(View.GONE);
        zakat_profesi_tips.setVisibility(View.VISIBLE);


        zakatprofesi_jumlahpenghasilan = rootView.findViewById(R.id.zakatprofesi_jumlahpenghasilan);
        zakatprofesi_hargaberas = rootView.findViewById(R.id.zakatprofesi_hargaberas);
        zakatprofesi_tanggal = rootView.findViewById(R.id.zakatprofesi_tanggal);


        isiBio1 = rootView.findViewById(R.id.isiBio1);
        isiBio2 = rootView.findViewById(R.id.isiBio2);
        isiBio3 = rootView.findViewById(R.id.isiBio3);
        zakat_profesi_nominal = rootView.findViewById(R.id.zakat_profesi_nominal);

        zakatprofesi_jumlahpenghasilan.addTextChangedListener(new Fungsi().formatRupiahEditText(zakatprofesi_jumlahpenghasilan));
        zakatprofesi_hargaberas.addTextChangedListener(new Fungsi().formatRupiahEditText(zakatprofesi_hargaberas));

        zakatprofesi_hitung = rootView.findViewById(R.id.zakatprofesi_hitung);
        zakatprofesi_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(zakatprofesi_jumlahpenghasilan.getText()) ) {
                    Snackbar.make(v,"Jumlah Penghasilan harus diisi!",Snackbar.LENGTH_LONG).show();
                }else if( TextUtils.isEmpty(zakatprofesi_hargaberas.getText()) ){
                    Snackbar.make(v,"Harga Beras harus diisi!",Snackbar.LENGTH_LONG).show();
                }else {

                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected void onPreExecute() {
                            pd = new ProgressDialog(getContext());
                            //pd.setTitle("Memproses...");
                            pd.setMessage("Silahkan tunggu sebentar.");
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setCancelable(false);
                            pd.setIndeterminate(true);
                            pd.show();
                        }

                        @Override
                        protected Void doInBackground(Void... arg0) {
                            try {
                                //Do something...
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            if (pd!=null) {
                                pd.dismiss();


                                zakat_profesi_calc.setVisibility(View.GONE);
                                zakat_profesi_result.setVisibility(View.VISIBLE);
                                zakat_profesi_tips.setVisibility(View.GONE);

                                String jumlah_penghasilan = String.valueOf(new Fungsi().formatInt(zakatprofesi_jumlahpenghasilan.getText().toString()));
                                String hargaberas = String.valueOf(new Fungsi().formatInt(zakatprofesi_hargaberas.getText().toString()));

                                int nilai_jumlah_penghasilan = Integer.parseInt(jumlah_penghasilan);
                                int nilai_hargaberas = Integer.parseInt(hargaberas);

                                int nilai_nishab = 520 * nilai_hargaberas;
                                int profesi_nominal = (int) ((nilai_jumlah_penghasilan * 2.5) / 100);

                                isiBio1.setText(new Fungsi().formatRupiah(nilai_jumlah_penghasilan));
                                isiBio2.setText(new Fungsi().formatRupiah(nilai_hargaberas));
                                isiBio3.setText(new Fungsi().formatRupiah(nilai_nishab));
                                zakat_profesi_nominal.setText(new Fungsi().formatRupiah(profesi_nominal));
                            }
                        }

                    };
                    task.execute((Void[])null);

                }

            }
        });
        zakat_profesi_reset = rootView.findViewById(R.id.zakat_profesi_reset);
        zakat_profesi_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zakat_profesi_calc.setVisibility(View.VISIBLE);
                zakat_profesi_result.setVisibility(View.GONE);
                zakat_profesi_tips.setVisibility(View.VISIBLE);

                zakatprofesi_jumlahpenghasilan.setText("");
                zakatprofesi_hargaberas.setText("");

            }
        });


        return rootView;
    }
}
