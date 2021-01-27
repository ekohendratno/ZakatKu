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

public class ZakatFidyahFragment extends Fragment {

    ProgressDialog pd;
    LinearLayout bayar_fidyah_calc,bayar_fidyah_result,bayar_fidyah_tips;
    AppCompatButton bayarfidyah_hitung;
    TextView bayar_fidyah_reset;
    TextInputEditText bayarfidyah_jumlahjiwafidyah,bayarfidyah_harga1porsimakan,bayarfidyah_jumlahharitidakpuasa;
    TextView isiBio1,isiBio2, isiBio3, bayar_fidyah_nominal;
    public Fragment newInstance(int position, String title) {
        ZakatFidyahFragment f = new ZakatFidyahFragment();

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
                R.layout.fragment_bayarfidyah, container, false);
        bayar_fidyah_calc = rootView.findViewById(R.id.bayar_fidyah_calc);
        bayar_fidyah_result = rootView.findViewById(R.id.bayar_fidyah_result);
        bayar_fidyah_tips = rootView.findViewById(R.id.bayar_fidyah_tips);

        bayar_fidyah_calc.setVisibility(View.VISIBLE);
        bayar_fidyah_result.setVisibility(View.GONE);
        bayar_fidyah_tips.setVisibility(View.VISIBLE);


        bayarfidyah_jumlahjiwafidyah = rootView.findViewById(R.id.bayarfidyah_jumlahjiawa);
        bayarfidyah_harga1porsimakan = rootView.findViewById(R.id.bayarfidyah_harga1porsimakan);
        bayarfidyah_jumlahharitidakpuasa = rootView.findViewById(R.id.bayarfidyah_jumlahharitidakpuasa);


        isiBio1 = rootView.findViewById(R.id.isiBio1);
        isiBio2 = rootView.findViewById(R.id.isiBio2);
        isiBio3 = rootView.findViewById(R.id.isiBio3);
        bayar_fidyah_nominal = rootView.findViewById(R.id.bayar_fidyah_nominal);

        bayarfidyah_harga1porsimakan.addTextChangedListener(new Fungsi().formatRupiahEditText(bayarfidyah_harga1porsimakan));

        bayarfidyah_hitung = rootView.findViewById(R.id.bayarfidyah_hitung);
        bayarfidyah_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(bayarfidyah_jumlahjiwafidyah.getText()) ) {
                    Snackbar.make(v,"Jumlah jiwa harus diisi!",Snackbar.LENGTH_LONG).show();
                }else if( TextUtils.isEmpty(bayarfidyah_harga1porsimakan.getText()) ){
                    Snackbar.make(v,"Harga 1 porsi makan harus diisi!",Snackbar.LENGTH_LONG).show();
                }else if( TextUtils.isEmpty(bayarfidyah_jumlahharitidakpuasa.getText()) ){
                    Snackbar.make(v,"Jumlah hari tidak puasa harus diisi!",Snackbar.LENGTH_LONG).show();
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

                                bayar_fidyah_calc.setVisibility(View.GONE);
                                bayar_fidyah_result.setVisibility(View.VISIBLE);
                                bayar_fidyah_tips.setVisibility(View.GONE);

                                String jumlahjiwafidyah = bayarfidyah_jumlahjiwafidyah.getText().toString();
                                String harga1porsimakan = String.valueOf(new Fungsi().formatInt(bayarfidyah_harga1porsimakan.getText().toString()));
                                String jumlahharitidakpuasa = bayarfidyah_jumlahharitidakpuasa.getText().toString();

                                int nilai_jumlahjiwafidyah = Integer.parseInt(jumlahjiwafidyah);
                                int nilai_harga1porsimakan = Integer.parseInt(harga1porsimakan);
                                int nilai_jumlahharitidakpuasa = Integer.parseInt(jumlahharitidakpuasa);

                                int fitrah_nominal = (int) (1) * (nilai_jumlahjiwafidyah) * (nilai_harga1porsimakan) * (nilai_jumlahharitidakpuasa);

                                isiBio1.setText(String.valueOf(nilai_jumlahjiwafidyah + " orang"));
                                isiBio2.setText(new Fungsi().formatRupiah(nilai_harga1porsimakan));
                                isiBio3.setText(String.valueOf(nilai_jumlahharitidakpuasa + " hari"));
                                bayar_fidyah_nominal.setText(new Fungsi().formatRupiah(fitrah_nominal));
                            }
                        }

                    };
                    task.execute((Void[])null);

                }

            }
        });
        bayar_fidyah_reset = rootView.findViewById(R.id.bayar_fidyah_reset);
        bayar_fidyah_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bayar_fidyah_calc.setVisibility(View.VISIBLE);
                bayar_fidyah_result.setVisibility(View.GONE);
                bayar_fidyah_tips.setVisibility(View.VISIBLE);

                bayarfidyah_jumlahjiwafidyah.setText("");
                bayarfidyah_harga1porsimakan.setText("");
                bayarfidyah_jumlahharitidakpuasa.setText("");
            }
        });


        return rootView;
    }
}
