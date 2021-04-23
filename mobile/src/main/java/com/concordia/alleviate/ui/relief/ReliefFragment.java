package com.concordia.alleviate.ui.relief;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.concordia.alleviate.R;
import com.concordia.alleviate.adapters.ReliefAdapter;
import com.concordia.alleviate.models.OnItemClickListener;
import com.concordia.alleviate.models.ReliefExercise;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

public class ReliefFragment extends Fragment {

    private static final String CAPABILITY_NAME = "start_alleviate";

    private String nodeId;
    private ReliefViewModel vm;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ReliefViewModel.class);
        context = getContext();

        View root = inflater.inflate(R.layout.fragment_relief, container, false);

        new Thread(this::getCompatibleWearableNodes).start();

        ReliefAdapter reliefAdapter = new ReliefAdapter(context, vm.getExercises(), this::sendMessageToWatch);

        ListView listView = root.findViewById(R.id.relief_list);
        listView.setAdapter(reliefAdapter);

        Chip meditationChip = root.findViewById(R.id.relief_chip_meditation);
        meditationChip.setChecked(vm.isShowMeditation());
        meditationChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vm.setShowMeditation(isChecked);
            ((ReliefAdapter) listView.getAdapter()).setData(vm.getExercises());
        });

        Chip breathingChip = root.findViewById(R.id.relief_chip_breathing);
        breathingChip.setChecked(vm.isShowBreathing());
        breathingChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vm.setShowBreathing(isChecked);
            ((ReliefAdapter) listView.getAdapter()).setData(vm.getExercises());
        });

        Chip groundingChip = root.findViewById(R.id.relief_chip_grounding);
        groundingChip.setChecked(vm.isShowGrounding());
        groundingChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vm.setShowGrounding(isChecked);
            ((ReliefAdapter) listView.getAdapter()).setData(vm.getExercises());
        });

        return root;
    }

    private void sendMessageToWatch(ReliefExercise i) {
        new Thread(() -> {
            if (nodeId == null) {
                showSnackbar(false);
                return;
            }
            Task<Integer> task = Wearable
                .getMessageClient(context)
                .sendMessage(nodeId, "/alleviate/now", i.getTitle().getBytes(StandardCharsets.UTF_8));
            task.addOnSuccessListener(integer -> {
                showSnackbar(true);
            });
            task.addOnFailureListener(e -> {
                showSnackbar(false);
            });
        }).start();
    }

    private void getCompatibleWearableNodes() {
        try {
            CapabilityInfo info = Tasks.await(
                Wearable
                    .getCapabilityClient(context)
                    .getCapability(
                        CAPABILITY_NAME,
                        CapabilityClient.FILTER_REACHABLE
                    )
            );
            updateSelectedNode(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CapabilityClient.OnCapabilityChangedListener listener = this::updateSelectedNode;
        Wearable.getCapabilityClient(context).addListener(listener, CAPABILITY_NAME);
    }

    private void updateSelectedNode(CapabilityInfo capabilityInfo) {
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        for (Node node : connectedNodes) {
            if (node.isNearby()) {
                nodeId = node.getId();
                return;
            }
            nodeId = node.getId();
        }
    }

    private void showSnackbar(boolean success) {
        int color = context.getColor(R.color.ming);
        int message = R.string.fab_snackbar_alert;

        if (!success) {
            color = context.getColor(R.color.fiery_rose);
            message = R.string.fab_snackbar_error;
        }

        Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(color);

        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);

        snackbar.show();
    }
}