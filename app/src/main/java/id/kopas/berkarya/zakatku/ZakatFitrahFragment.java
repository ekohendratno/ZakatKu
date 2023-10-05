package id.kopas.berkarya.zakatku;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import id.kopas.berkarya.zakatku.utils.Fungsi;

public class ZakatFitrahFragment extends Fragment {

    ProgressDialog pd;
    LinearLayout zakat_fitrah_calc,zakat_fitrah_result,zakat_fitrah_tips;
    AppCompatButton zakatfitrah_hitung;
    TextView zakat_fitrah_reset;
    TextInputEditText zakatfitrah_jumlahjiwa,zakatfitrah_hargaberas,zakatfitrah_tanggal;
    TextView isiBio1,isiBio2, isiBio3, zakat_fitrah_nominal;
    public Fragment newInstance(int position, String title) {
        ZakatFitrahFragment f = new ZakatFitrahFragment();

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
                R.layout.fragment_zakatfitrah, container, false);
        zakat_fitrah_calc = rootView.findViewById(R.id.zakat_fitrah_calc);
        zakat_fitrah_result = rootView.findViewById(R.id.zakat_fitrah_result);
        zakat_fitrah_tips = rootView.findViewById(R.id.zakat_fitrah_tips);

        zakat_fitrah_calc.setVisibility(View.VISIBLE);
        zakat_fitrah_result.setVisibility(View.GONE);
        zakat_fitrah_tips.setVisibility(View.VISIBLE);


        zakatfitrah_jumlahjiwa = rootView.findViewById(R.id.zakatfitrah_jumlahjiwa);
        zakatfitrah_hargaberas = rootView.findViewById(R.id.zakatfitrah_hargaberas);
        zakatfitrah_tanggal = rootView.findViewById(R.id.zakatfitrah_tanggal);


        isiBio1 = rootView.findViewById(R.id.isiBio1);
        isiBio2 = rootView.findViewById(R.id.isiBio2);
        isiBio2 = rootView.findViewById(R.id.isiBio2);
        zakat_fitrah_nominal = rootView.findViewById(R.id.zakat_fitrah_nominal);

        //zakatfitrah_jumlahjiwa.addTextChangedListener(new Berzakat().formatRupiahEditText(zakatfitrah_jumlahjiwa));
        zakatfitrah_hargaberas.addTextChangedListener(new Fungsi().formatRupiahEditText(zakatfitrah_hargaberas));

        zakatfitrah_hitung = rootView.findViewById(R.id.zakatfitrah_hitung);
        zakatfitrah_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(zakatfitrah_jumlahjiwa.getText()) ) {
                    Snackbar.make(v,"Jumlah Jiwa harus diisi!",Snackbar.LENGTH_LONG).show();
                }else if( TextUtils.isEmpty(zakatfitrah_hargaberas.getText()) ){
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

                                zakat_fitrah_calc.setVisibility(View.GONE);
                                zakat_fitrah_result.setVisibility(View.VISIBLE);
                                zakat_fitrah_tips.setVisibility(View.GONE);


                                String jumlah_jiwa = zakatfitrah_jumlahjiwa.getText().toString();
                                String hargaberas = String.valueOf(new Fungsi().formatInt(zakatfitrah_hargaberas.getText().toString()));

                                int nilai_jumlah_jiwa = Integer.parseInt(jumlah_jiwa);
                                int nilai_hargaberas = Integer.parseInt(hargaberas);

                                int fitrah_nominal = (int) ((nilai_hargaberas * 3.5) * nilai_jumlah_jiwa);

                                isiBio1.setText(String.valueOf(nilai_jumlah_jiwa + " orang"));
                                isiBio2.setText(new Fungsi().formatRupiah(nilai_hargaberas));
                                zakat_fitrah_nominal.setText(new Fungsi().formatRupiah(fitrah_nominal));
                            }
                        }

                    };
                    task.execute((Void[])null);

                }


            }
        });
        zakat_fitrah_reset = rootView.findViewById(R.id.zakat_fitrah_reset);
        zakat_fitrah_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zakat_fitrah_calc.setVisibility(View.VISIBLE);
                zakat_fitrah_result.setVisibility(View.GONE);
                zakat_fitrah_tips.setVisibility(View.VISIBLE);

                zakatfitrah_jumlahjiwa.setText("");
                zakatfitrah_hargaberas.setText("");
            }
        });


        return rootView;
    }
}
