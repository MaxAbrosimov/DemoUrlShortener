import React, { Component } from 'react';
import {Container, FormGroup, Label} from "reactstrap";
import NavBar from "./NavBar";

class PreRedirectPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: true,
            errorMessage: ''
        }
    }

    async componentDidMount() {
        const shortUrl = this.props.match.params.shortUrl;
        if (shortUrl) {
            await fetch('/v1/url/getByShort', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({shortUrl}),
            }).then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    this.setState({ loading: false, message: response.statusText });
                    return {};
                }
            }).then(data => {
                if (data) {
                    window.open(data.longUrl, '_self', 'noopener,noreferrer')
                }
            })
        }
    }

    render() {
        const {loading, message} = this.state;
            return <div>
                <NavBar/>
                <Container>
                    <FormGroup>
                        {loading && <p>Loading...</p>}
                    </FormGroup>
                    <FormGroup>
                        {
                            message && (
                                <div>
                                    <Label style={{color: "red"}}>{message}</Label>
                                </div>
                            )
                        }
                    </FormGroup>
                </Container>
            </div>;
    }
}

export default PreRedirectPage;