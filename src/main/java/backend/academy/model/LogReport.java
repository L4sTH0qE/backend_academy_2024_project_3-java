package backend.academy.model;

import com.google.common.math.Quantiles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/// Класс, хранящий информацию для отчета, полученную при обработке логов.
@Getter
public class LogReport {

    private final List<String> files = new ArrayList<>();

    private final List<Integer> bytesSent = new ArrayList<>();

    private final Map<Integer, Integer> statusMap = new HashMap<>();

    private final Map<String, Integer> resourceMap = new HashMap<>();

    private String fromDate = "-";

    private String toDate = "-";

    private int totalRequests = 0;

    public void updateFiles(String file) {
        files.add(file);
    }

    public void updateFromDate(LocalDate fromDate) {
        this.fromDate = fromDate.toString();
    }

    public void updateToDate(LocalDate toDate) {
        this.toDate = toDate.toString();
    }

    public void updateTotalRequests() {
        ++totalRequests;
    }

    public void updateBytesSent(int bytes) {
        bytesSent.add(bytes);
    }

    public void updateRequestInfo(int status, String resource) {
        statusMap.put(status, statusMap.getOrDefault(status, 0) + 1);
        resourceMap.put(resource, resourceMap.getOrDefault(resource, 0) + 1);
    }

    public int getBytesMedian() {
        double result = Quantiles.median().compute(bytesSent);
        return (int) result;
    }

    public int getBytesPercentile(int percentile) {
        double result = Quantiles.percentiles().index(percentile).compute(bytesSent);
        return (int) result;
    }

    public List<Map.Entry<Integer, Integer>> getMostFrequentStatuses() {
        return statusMap.entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .limit(3)
            .toList(); // Собираем результат в список
    }

    public List<Map.Entry<String, Integer>> getMostFrequentResources() {
        return resourceMap.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(3)
            .toList(); // Собираем результат в список
    }
}
