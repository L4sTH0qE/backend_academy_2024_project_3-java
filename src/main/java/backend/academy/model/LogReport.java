package backend.academy.model;

import com.google.common.math.Quantiles;
import com.google.common.math.Stats;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

/// Класс, хранящий информацию для отчета, полученную при обработке логов.
@Getter
public class LogReport {

    private static final int MAX_LIMIT = 3;

    private final List<String> files = new ArrayList<>();

    private final Set<String> remoteAddresses = new HashSet<>();

    private final List<Integer> bytesSent = new ArrayList<>();

    private final Map<Integer, Integer> statusMap = new HashMap<>();

    private final Map<String, Integer> resourceMap = new HashMap<>();

    private LocalDate fromDate;

    private LocalDate toDate;

    private int totalRequests = 0;

    public void updateFiles(String file) {
        files.add(file);
    }

    public void updateRemoteAddresses(String remoteAddr) {
        remoteAddresses.add(remoteAddr);
    }

    public void updateFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void updateToDate(LocalDate toDate) {
        this.toDate = toDate;
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

    public int getBytesMean() {
        double result = Stats.meanOf(bytesSent);
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
            .limit(MAX_LIMIT)
            .toList(); // Собираем результат в список
    }

    public List<Map.Entry<String, Integer>> getMostFrequentResources() {
        return resourceMap.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(MAX_LIMIT)
            .toList(); // Собираем результат в список
    }
}
