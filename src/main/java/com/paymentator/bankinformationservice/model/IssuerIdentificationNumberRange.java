package com.paymentator.bankinformationservice.model;

public final class IssuerIdentificationNumberRange {
    public static final int IIN_LENGTH = 6;

    private final Integer minRange;
    private final Integer maxRange;

    public IssuerIdentificationNumberRange(Integer minRange, Integer maxRange) {
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public Integer getMinRange() {
        return minRange;
    }

    public Integer getMaxRange() {
        return maxRange;
    }


    @Override
    public String toString() {
        return "Min: " + minRange + "Max: " + maxRange;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maxRange == null) ? 0 : maxRange.hashCode());
        result = prime * result + ((minRange == null) ? 0 : minRange.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IssuerIdentificationNumberRange other = (IssuerIdentificationNumberRange) obj;
        if (maxRange == null) {
            if (other.maxRange != null)
                return false;
        } else if (!maxRange.equals(other.maxRange))
            return false;
        if (minRange == null) {
            if (other.minRange != null)
                return false;
        } else if (!minRange.equals(other.minRange))
            return false;
        return true;
    }


}
