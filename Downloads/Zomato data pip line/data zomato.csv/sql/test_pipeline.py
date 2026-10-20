
import pandas as pd

from transform import transform_data


def test_transform_removes_duplicates():
    data = {
        "restaurant": ["A2B", "A2B", "Saravana Bhavan"],
        "city": ["Chennai", "Chennai", "Chennai"]
    }

    df = pd.DataFrame(data)

    result = transform_data(df)

    assert len(result) == 2
    print("PASS: Duplicate rows removed")


def test_transform_strips_spaces():
    data = {
        "restaurant": [" A2B "],
        "city": [" Chennai "]
    }

    df = pd.DataFrame(data)

    result = transform_data(df)

    assert result.iloc[0]["restaurant"] == "A2B"
    assert result.iloc[0]["city"] == "Chennai"
    print("PASS: Extra spaces removed")


if __name__ == "__main__":
    test_transform_removes_duplicates()
    test_transform_strips_spaces()

    print("\nAll tests passed!")
