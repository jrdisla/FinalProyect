package jdisa.homesafety.Menu.Activity;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jdisa.homesafety.Data_Form.ImagaBuena;
import jdisa.homesafety.R;

/**
 * Created by jrdis on 16/12/2017.
 */

public class Fotos_buenas_adapter extends ArrayAdapter <ImagaBuena> {
    private Activity context;
    private  int resource;
    private List<ImagaBuena> listImage;

    public Fotos_buenas_adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImagaBuena> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource,null);
        TextView tvName = (TextView) v.findViewById(R.id.tvImageName2);
        ImageView img = (ImageView) v.findViewById(R.id.imgView2);
        tvName.setText(listImage.get(position).getName());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        return v;

    }
}
