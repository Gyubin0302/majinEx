import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow import keras
from sklearn.model_selection import train_test_split
from tensorflow.keras.callbacks import EarlyStopping
from keras.models import load_model
import matplotlib.pyplot as plt

import os
path_dir = 'D:/data/final/busan'
file_list = os.listdir(path_dir)

for file in file_list:
    print(file)
    df = pd.read_csv('D:/data/final/busan/'+file)
    #pd.set_option('display.max_columns',20)
    #df.describe()
    #df.columns

    del df['Unnamed: 0']
    del df['weather']
    del df['ord']
    del df['rating']

    df.astype(int)

    df, test_df = train_test_split(df, test_size=0.2, random_state=42, shuffle=True)

    Y_train = df["lab"]
    X_train = df.drop("lab",axis=1)
    Y_test = test_df["lab"]
    X_test = test_df.drop("lab",axis=1)
    #X_train.shape, Y_train.shape, X_test.shape, Y_test.shape

    model = keras.Sequential([
        keras.layers.Flatten(input_shape=(9,)),
        keras.layers.Dense(128, activation='relu'),
        keras.layers.Dense(2, activation='softmax')
    ])

    model.compile(optimizer='adam',
                  loss='sparse_categorical_crossentropy',
                  metrics=['accuracy'])

#-------------------------------------------------------------------------------------
    early_stopping = EarlyStopping(patience=30)
    hist = model.fit(X_train,Y_train,epochs=1000, batch_size=10, verbose=2, validation_data=(X_test, Y_test),callbacks=[early_stopping])

    model.save('D:/data/final/busan/'+file[:-4]+'.h5')

    fig, loss_ax = plt.subplots()

    acc_ax = loss_ax.twinx()

    loss_ax.plot(hist.history['loss'], 'y', label='train loss')
    loss_ax.plot(hist.history['val_loss'], 'r', label='test loss')

    acc_ax.plot(hist.history['accuracy'], 'b', label='train acc')
    acc_ax.plot(hist.history['val_accuracy'], 'g', label='test acc')

    loss_ax.set_xlabel('epoch')
    loss_ax.set_ylabel('loss')
    acc_ax.set_ylabel('accuray')

    loss_ax.legend(loc='upper left')
    acc_ax.legend(loc='lower left')

    plt.show()
#-------------------------------------------------------------------------------------
'''
df = pd.read_csv('d:/data/final/jeju/jeju_800_21.csv')
del df['Unnamed: 0']
del df['ord']
del df['rating']
df.astype(int)
df, test_df = train_test_split(df, test_size=0.2, random_state=42, shuffle=True)
Y_val = df["lab"]
X_val = df.drop("lab",axis=1)
Y_val2 = test_df["lab"]
X_val2 = test_df.drop("lab",axis=1)
test_loss, test_acc = model.evaluate(X_val, Y_val)
print('acc:', test_acc)
print('loss:', test_loss)
test_loss, test_acc = model.evaluate(X_val2, Y_val2)
print('acc:', test_acc)
print('loss:', test_loss)
'''

#-------------------------------------------------------------------------------------

model.fit(X_train,Y_train,epochs=100, verbose=2)

test_loss, test_acc = model.evaluate(X_test, Y_test)

print('loss:', test_loss)
print('acc:', test_acc)

#-------------------------------------------------------------------------------------
X_real = pd.read_csv('d:/data/final/real/seoul/seoul_20201219_1_1200_46.csv')
X_real
del X_real['Unnamed: 0']
del X_real['rating']
del X_real['sttime']
df.astype(int)

predictions = model.predict(X_real)
predictions

for i in range(0,len(predictions)):
    print(np.argmax(predictions[i]),end=', ')

Y_test.tolist()
