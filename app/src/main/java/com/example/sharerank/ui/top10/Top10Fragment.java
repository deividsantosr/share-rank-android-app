package com.example.sharerank.ui.top10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sharerank.DownloadImageTask;
import com.example.sharerank.R;

public class Top10Fragment extends Fragment {
    public static FragmentTransaction top10FragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        top10FragmentTransaction = getFragmentManager().beginTransaction();

        ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView_1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView_2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.imageView_3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.imageView_4);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.imageView_5);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.imageView_6);
        ImageView imageView7 = (ImageView) view.findViewById(R.id.imageView_7);
        ImageView imageView8 = (ImageView) view.findViewById(R.id.imageView_8);
        ImageView imageView9 = (ImageView) view.findViewById(R.id.imageView_9);
        ImageView imageView10 = (ImageView) view.findViewById(R.id.imageView_10);

        new DownloadImageTask(imageView1).execute("https://lh5.googleusercontent.com/p/AF1QipM5FGFEI3ozH1vlyUBDI-CHO3a3HMck88Fs1_jG=w408-h271-k-no");
        new DownloadImageTask(imageView2).execute("https://lh5.googleusercontent.com/p/AF1QipNObPvz1j-9VA3C8uHOSNMsO66xQ0Rt8poenL2S=w408-h306-k-no");
        new DownloadImageTask(imageView3).execute("https://lh5.googleusercontent.com/p/AF1QipOKxLxasjT25RMaPTpnuwbbcojAHdhJTdRDUkD2=w408-h418-k-no");
        new DownloadImageTask(imageView4).execute("https://lh5.googleusercontent.com/p/AF1QipOszR1I2jPEtvZubFAHBbgZV_eeu_l3-EmPilI5=w408-h306-k-no");
        new DownloadImageTask(imageView5).execute("https://lh5.googleusercontent.com/proxy/8o-hyCmFc1_KxB4sCFjACiJZx21YJVZThZREMSn0wJsrl_qOFMfwUuMPMoGoRM04QW4Kb0CSpWqDp8aD2Cv7GzjvJHKtUzk921_twsgfUGtP1R9j4U13d1k64fZU_N0Ll4g-W6umv23vRcJtVvHLf7D3NywBFDw=s387-k-no");
        new DownloadImageTask(imageView6).execute("https://lh5.googleusercontent.com/p/AF1QipPYBJQdJBqmeSYfutAiAM9Z5N6FqM_C4tC7025C=w408-h306-k-no");
        new DownloadImageTask(imageView7).execute("https://lh5.googleusercontent.com/p/AF1QipNFh5bXNI2Vwdutymxf3c5tc9ehcIL2AMYfbvRD=w408-h272-k-no");
        new DownloadImageTask(imageView8).execute("https://lh5.googleusercontent.com/p/AF1QipMqJy_WHpPXTP6Dvl_yKXzDIUiOi3EEHzpdxRo=w408-h306-k-no");
        new DownloadImageTask(imageView9).execute("https://lh5.googleusercontent.com/p/AF1QipNJ3bPQFuDP8VggNUmlA2U399kpoXjvgKpDiUkF=w408-h306-k-no");
        new DownloadImageTask(imageView10).execute("https://lh5.googleusercontent.com/p/AF1QipNmlbe6FEW5UEZps5ZYlTmutmn3-YLSUd5NtvuE=w408-h272-k-no");
    }
}

