'use babel';
import React from 'react';

export default class UserProfile extends React.Component
{
    render()
    {
        return (
            <div style={ { height: "75px" , backgroundImage:"url(http://constructs.stampede-design.com/wp-content/uploads/2014/07/gradient-background.jpg)"} }>
                <div style={ { color: "white", textAlign: "center", fontSize: "20" } }>{ this.props.username }</div>
            </div>

        );
    }
}

/*
<section data-grid="col-12" class="m-highlight-feature f-align-left" itemscope itemtype="https://schema.org/Product">
            <picture class="c-image">
                    <source srcset="http://placehold.it/1600x600" media="(min-width: 200px)" />
                 <img srcset="http://placehold.it/1259x472" src="http://placehold.it/1259x472" alt="Placeholder with grey background and dimension watermark without any imagery" />
            </picture>
    <div>
        <h2 class="c-heading">Heading</h2>
        <p class="c-paragraph">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
        <a href="#" class="c-call-to-action c-glyph">
            <span>CALL TO ACTION</span>
        </a>
    </div>
            </section> */