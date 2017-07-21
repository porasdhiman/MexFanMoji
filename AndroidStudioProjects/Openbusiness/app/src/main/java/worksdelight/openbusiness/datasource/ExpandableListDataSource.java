package worksdelight.openbusiness.datasource;

import android.content.Context;



import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import worksdelight.openbusiness.R;

/**
 * Created by msahakyan on 22/10/15.
 */
public class ExpandableListDataSource {

    /**
     * Returns fake data of films
     *
     * @param context
     * @return
     */
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new TreeMap<>();

        List<String> type = Arrays.asList(context.getResources().getStringArray(R.array.type));

        List<String> actionSelling = Arrays.asList(context.getResources().getStringArray(R.array.actionSelling));
        List<String> relationShip = Arrays.asList(context.getResources().getStringArray(R.array.relationship));
        List<String> settings = Arrays.asList(context.getResources().getStringArray(R.array.settings));
        List<String> report = Arrays.asList(context.getResources().getStringArray(R.array.report));
        List<String> help = Arrays.asList(context.getResources().getStringArray(R.array.help));
        List<String> legal = Arrays.asList(context.getResources().getStringArray(R.array.legal));

        expandableListData.put(type.get(0), actionSelling);
        expandableListData.put(type.get(1), relationShip);
        expandableListData.put(type.get(2), settings);
        expandableListData.put(type.get(3), report);
        expandableListData.put(type.get(4), help);
        expandableListData.put(type.get(5), legal);

        return expandableListData;
    }
}
