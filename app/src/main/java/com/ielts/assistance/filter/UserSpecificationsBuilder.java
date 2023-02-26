package com.ielts.assistance.filter;

import com.google.common.primitives.Ints;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public UserSpecificationsBuilder() {
        params = new ArrayList<>();
    }
    public UserSpecificationsBuilder with(
            String key, String operation, Object value, String prefix, String suffix) {

        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        boolean isNumber = StringUtils.isNumeric(value.toString());
        if (isNumber) {
            value = Ints.tryParse(value.toString());
        }
        if (op != null) {
            if (op == SearchOperation.EQUALITY) {
                boolean startWithAsterisk = prefix.contains("*");
                boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(key, op, value));
        }
        return this;
    }

    public org.springframework.data.jpa.domain.Specification<?> build() {
        if (params.size() == 0) {
            return null;
        }

        org.springframework.data.jpa.domain.Specification result = new Specification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? org.springframework.data.jpa.domain.Specification.where(result).or(new Specification(params.get(i)))
                    : org.springframework.data.jpa.domain.Specification.where(result).and(new Specification(params.get(i)));
        }

        return result;
    }

}
