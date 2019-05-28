package sg.edu.rp.c347.p06_taskmanager;

import android.widget.ArrayAdapter;

public class TaskAdapter extends ArrayAdapter<Task> {
        Context context;
        ArrayList<Song> songs;
        int resource;
        ImageView iv1, iv2, iv3, iv4, iv5;
        TextView tvYear, tvTitle, tvSinger;

public CustomAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.songs = songs;
        this.resource = resource;

        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        tvYear = rowView.findViewById(R.id.tvYear);
        tvTitle = rowView.findViewById(R.id.tvName);
        tvSinger = rowView.findViewById(R.id.tvSinger);
        iv1 = rowView.findViewById(R.id.imageView1star);
        iv2 = rowView.findViewById(R.id.imageView2star);
        iv3 = rowView.findViewById(R.id.imageView3star);
        iv4 = rowView.findViewById(R.id.imageView4star);
        iv5 = rowView.findViewById(R.id.imageView5star);

        //Match the UI components with Java variables

        Song song = songs.get(position);
        tvYear.setText(String.valueOf(song.getYear()));
        tvTitle.setText(song.getTitle());
        tvSinger.setText(song.getSingers());

        int stars = song.getStars();


        //Check if the property for starts >= 5, if so, "light" up the stars
        if (stars >= 5) {
        iv5.setImageResource(android.R.drawable.btn_star_big_on);
        iv4.setImageResource(android.R.drawable.btn_star_big_on);
        iv3.setImageResource(android.R.drawable.btn_star_big_on);
        iv2.setImageResource(android.R.drawable.btn_star_big_on);
        iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 4) {
        iv5.setImageResource(android.R.drawable.btn_star_big_off);
        iv4.setImageResource(android.R.drawable.btn_star_big_on);
        iv3.setImageResource(android.R.drawable.btn_star_big_on);
        iv2.setImageResource(android.R.drawable.btn_star_big_on);
        iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 3) {
        iv5.setImageResource(android.R.drawable.btn_star_big_off);
        iv4.setImageResource(android.R.drawable.btn_star_big_off);
        iv3.setImageResource(android.R.drawable.btn_star_big_on);
        iv2.setImageResource(android.R.drawable.btn_star_big_on);
        iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 2) {
        iv5.setImageResource(android.R.drawable.btn_star_big_off);
        iv4.setImageResource(android.R.drawable.btn_star_big_off);
        iv3.setImageResource(android.R.drawable.btn_star_big_off);
        iv2.setImageResource(android.R.drawable.btn_star_big_on);
        iv1.setImageResource(android.R.drawable.btn_star_big_on);
        } else if (stars == 1) {
        iv5.setImageResource(android.R.drawable.btn_star_big_off);
        iv4.setImageResource(android.R.drawable.btn_star_big_off);
        iv3.setImageResource(android.R.drawable.btn_star_big_off);
        iv2.setImageResource(android.R.drawable.btn_star_big_off);
        iv1.setImageResource(android.R.drawable.btn_star_big_on);

        } else {

        iv5.setImageResource(android.R.drawable.btn_star_big_off);
        iv4.setImageResource(android.R.drawable.btn_star_big_off);
        iv3.setImageResource(android.R.drawable.btn_star_big_off);
        iv2.setImageResource(android.R.drawable.btn_star_big_off);
        iv1.setImageResource(android.R.drawable.btn_star_big_off);

        }
        return rowView;
        }
        }
