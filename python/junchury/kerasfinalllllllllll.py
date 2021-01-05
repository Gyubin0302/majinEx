import pathlib

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers

df = pd.read_csv('d:/data/rank1_1400_seoul_4st.csv')

pd.set_option('display.max_columns',20)
del df['Unnamed: 0']
df.columns
df.info()
df.describe()

df1 = pd.read_csv('d:/data/jockey.csv')
del df1['Unnamed: 0']
df1.columns
df1.info()
df1.describe()

df = pd.merge(df,df1,how='left',on='jkno')

df.to_csv('d:/data/rank1_1400_seoul_5st.csv',encoding='utf-8-sig')

sns.pairplot(df[["wgbudam", "ord", "chulno", "age", "rating", "rctime", "wghr1", "wghr2", "rctime1","jconsecutivewinningp"]], diag_kind="kde")
plt.show()