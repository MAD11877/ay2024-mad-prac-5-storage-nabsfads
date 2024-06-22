package sg.edu.np.mad.madpractical5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<sg.edu.np.mad.madpractical5.User> userList;
    private sg.edu.np.mad.madpractical5.ListActivity listActivity;

    public UserAdapter(List<sg.edu.np.mad.madpractical5.User> userList, sg.edu.np.mad.madpractical5.ListActivity listActivity) {
        this.userList = userList;
        this.listActivity = listActivity;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        sg.edu.np.mad.madpractical5.User user = userList.get(position);
        holder.name.setText(user.name);
        holder.description.setText(user.description);

        // Check if the last digit of the name is 7
        boolean hasSeven = user.name.endsWith("7");

        // Show or hide the large image based on the condition
        holder.largeImage.setVisibility(hasSeven ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> listActivity.onUserClicked(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        ImageView largeImage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            largeImage = itemView.findViewById(R.id.large_image);
        }
    }
}
