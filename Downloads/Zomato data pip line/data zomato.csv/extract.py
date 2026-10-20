
import pandas as pd
from pathlib import Path


def extract_data():
    file_path = Path("data/raw/zomato.csv")

    if not file_path.exists():
        raise FileNotFoundError(
            f"Dataset not found: {file_path}. "
            "Place your Zomato CSV inside data/raw/"
        )

    df = pd.read_csv(file_path)

    print("Data extracted successfully!")
    print("Rows and columns:", df.shape)
    print(df.head())

    return df


if __name__ == "__main__":
    extract_data()
