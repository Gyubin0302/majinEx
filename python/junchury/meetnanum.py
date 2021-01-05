# data analysis and wrangling
import pandas as pd
import numpy as np

df = pd.read_csv("D:/data/horse_total_seoul.csv")
df
df = df[df['rank']=="국5등급"]
df
df = df[df['rcdist']==1300]
df.columns
del df['Unnamed: 0']
del df['Unnamed: 0.1']

df.to_csv('d:/data/rank5_1300_seoul.csv',encoding='utf-8-sig')