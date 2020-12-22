package com.tj.cardsagainsthumanity.models.bulk;

import java.util.List;

public class BulkReport {
    private Integer total;
    private Integer successes;
    private Integer failures;
    private List<CardError> errors;

    public BulkReport(Integer total, Integer successes, Integer failures, List<CardError> errors) {
        this.total = total;
        this.successes = successes;
        this.failures = failures;
        this.errors = errors;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getSuccesses() {
        return successes;
    }

    public Integer getFailures() {
        return failures;
    }

    public List<CardError> getErrors() {
        return errors;
    }
}
