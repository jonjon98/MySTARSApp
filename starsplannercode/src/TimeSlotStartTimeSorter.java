import java.util.Comparator;

public class TimeSlotStartTimeSorter implements Comparator<TimeSlot>	{
	
    public int compare(TimeSlot o1, TimeSlot o2) {
    	int result = 0;
    	if(o1.getStartTime() < o2.getStartTime()) {
    		result = -1;
    	}
    	else if (o1.getStartTime() > o2.getStartTime()) {
    		result = 1;
    	}
        return result;
    }
}
