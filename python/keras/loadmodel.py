from keras.utils import np_utils
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dense, Activation
import numpy as np
import pandas as pd
from numpy import argmax

from tensorflow.keras.models import load_model

import os
model_dir = 'D:/data/final/seoul/'
race_dir = 'D:/data/final/real/seoul/'
race_list = os.listdir(race_dir)

df = pd.DataFrame(columns=['key', 'chulno','value'])
for race in race_list:
    temp = race.split('_')
    model_name = model_dir+temp[0]+'_'+temp[3]+'_'+temp[4][:-4]+'.h5'
    model = load_model(model_name)

    X_real = pd.read_csv(race_dir+race)
    X_real=X_real.sort_values(by='chulno',ascending=True)
    
    del X_real['Unnamed: 0']
    del X_real['rating']
    del X_real['sttime']
    X_real.astype(int)
    
    predictions = model.predict(X_real)
    #print(temp[0]+'_'+temp[1]+'_'+temp[2])
    #print(predictions)
    df=df.append({'key':temp[0]+'_'+temp[1]+'_'+temp[2],'chulno':X_real['chulno'].tolist(),'value':predictions.tolist()},ignore_index=True)

df.to_csv('d:/data/final/real/seoul.csv',encoding='utf-8-sig')


###############################
model = load_model('D:/data/final/busan/busan_1200_46.h5')

X_real = pd.read_csv('D:/data/final/real/busan/busan_20201219_2_1200_46.csv')
X_real

del X_real['Unnamed: 0']
del X_real['rating']
del X_real['sttime']
df.astype(int)

predictions = model.predict(X_real)
print(predictions.tolist())
type(predictions)

for i in range(0,len(predictions)):
    print(np.argmax(predictions[i]),end=', ')