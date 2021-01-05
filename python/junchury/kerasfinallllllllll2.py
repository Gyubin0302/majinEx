import pathlib

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers

df = pd.read_csv('d:/data/rank1_1400_seoul_3st.csv')

df.columns

df['lab'] = df['ord'].apply(lambda x: 0 if x<4 else 1)

sns.pairplot(df[["wgbudam", "ord", "chulno", "age", "rating", "rctime", "wghr1", "wghr2", "rctime1","jconsecutivewinningp","lab"]], diag_kind="kde")
plt.show()