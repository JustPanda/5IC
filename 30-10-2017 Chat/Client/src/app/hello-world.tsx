
import React from 'react';

export default class HelloWorld extends React.Component<{}, {}> {

    public render() {
        return (
            <div id="myControl" data-win-control="WinJS.UI.Rating" data-win-options="{averageRating: 3.4, maxRating: 10,  onchange: basics.changeRating }" >
            
            </div>
        );
    }
}
