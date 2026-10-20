
import pandas as pd


def transform_data(df):
    print("Starting data cleaning...")

    # Make a copy of the original data
    df = df.copy()

    # Remove duplicate rows
    df = df.drop_duplicates()

    # Remove extra spaces from column names
    df.columns = df.columns.str.strip()

    # Clean text columns
    for column in df.select_dtypes(include=["object"]).columns:
        df[column] = df[column].map(
            lambda value: value.strip()
            if isinstance(value, str)
            else value
        )

    # Convert common missing-value strings into actual nulls
    df = df.replace(
        ["", "NA", "N/A", "null", "None"],
        pd.NA
    )

    print("Data cleaning completed!")
    print("Rows and columns after cleaning:", df.shape)
    print("Missing values per column:")
    print(df.isnull().sum())

    return df


if __name__ == "__main__":
    print("Run pipeline.py to execute the complete pipeline.")
