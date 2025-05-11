package com.example.javabiometric;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InsightAdapter extends RecyclerView.Adapter<InsightAdapter.InsightViewHolder> {

    private final List<String> insights;

    public InsightAdapter(List<String> insights) {
        this.insights = insights;
    }

    @NonNull
    @Override
    public InsightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insight_item, parent, false);
        return new InsightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsightViewHolder holder, int position) {
        String insight = insights.get(position);
        holder.insightTextView.setText(insight);
    }

    @Override
    public int getItemCount() {
        return insights.size();
    }

    static class InsightViewHolder extends RecyclerView.ViewHolder {
        TextView insightTextView;

        InsightViewHolder(View itemView) {
            super(itemView);
            insightTextView = itemView.findViewById(R.id.insightTextView);
        }
    }
}
