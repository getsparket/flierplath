below are the instructions for re-navigate, the example app i'm working from. the instructions should work, with some fiddling, for iOS. i've probably forgotten what it took to set this up. so if you run into problems, maybe just come by and i'll set you up.

basically, follow [these directions](https://github.com/vikeri/re-navigate)


to get the android version running, you'll have to do some stuff that you'll figure out on your own, or i'll tell you.
 - i had to make a local.properties file in $proj-dir/android/ with contents:
sdk.dir=/Users/$your-user-name/Library/sdk  (you might not if you follow these directions: https://facebook.github.io/react-native/releases/0.23/docs/android-setup.html)






# re-navigate
> Example of React Navigation with [re-frame](https://github.com/Day8/re-frame)/[re-natal](https://github.com/drapanjanas/re-natal/)


This example uses React Native's new Navigation component [React Navigation](https://reactnavigation.org/) that eventually will replace the current React Native navigation solutions (or so they say).

The one thing that does not work is to read the state of the tabs in `re-frame`. If you do that the tab switching will flicker. So currently it is a hack that saves the state to the re-frame db but uses the Navigators own state management to actually update the state.

If someone wants to give the full `re-frame` solution a stab that can be found on commit 78c79aa.

Reading the state of the stack from re-frame works just fine.

## Example code

It is based on the scaffold from [re-natal](https://github.com/drapanjanas/re-natal/), almost everything is found in [re-navigate.ios.core](src/re_navigate/ios/core.cljs)

## Run

Requirements: 
- node & npm/yarn
- leiningen `brew install leiningen`
- re-natal & react-native-cli `npm install -g re-natal react-native-cli` 

`cd` into the directory.

```
yarn && lein prod-build && react-native run-ios
```

## Notes

- React (15.4.2)
- React Native (0.40.0)



## Contributors

Viktor Eriksson ([vikeri](https://github.com/vikeri))

Anthony Mittaz ([sync](https://github.com/sync))

Sam Liu ([wind13](https://github.com/wind13))
